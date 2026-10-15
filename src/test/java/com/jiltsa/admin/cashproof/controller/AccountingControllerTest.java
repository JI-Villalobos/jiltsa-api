package com.jiltsa.admin.cashproof.controller;

import com.jiltsa.admin.cashproof.persistence.entity.Accounting;
import com.jiltsa.admin.cashproof.persistence.entity.ExpenseRegistry;
import com.jiltsa.admin.cashproof.persistence.entity.IncomeRegistry;
import com.jiltsa.admin.cashproof.persistence.repository.AccountingRepository;
import com.jiltsa.admin.cashproof.persistence.repository.ExpenseRegistryRepository;
import com.jiltsa.admin.cashproof.persistence.repository.IncomeRegistryRepository;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Accounting responses embed the income/expense registries of every row. With
 * open-in-view off, that walk must happen inside the service transaction, and
 * with batch fetching it must not cost one query per accounting.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountingControllerTest {
    // seeded by db/testdata and not used by the other tests
    private static final int BRANCH_ID = 3;
    private static final int SELLER_ID = 3;
    private static final int ACCOUNTINGS = 3;

    @Autowired private MockMvc mockMvc;
    @Autowired private AccountingRepository accountingRepository;
    @Autowired private IncomeRegistryRepository incomeRegistryRepository;
    @Autowired private ExpenseRegistryRepository expenseRegistryRepository;
    @Autowired private EntityManagerFactory entityManagerFactory;

    @BeforeAll
    void seed() {
        for (int i = 0; i < ACCOUNTINGS; i++) {
            Accounting accounting = accountingRepository.save(
                    new Accounting(SELLER_ID, BRANCH_ID, LocalDateTime.now().minusHours(i)));
            for (int j = 0; j < 2; j++) {
                incomeRegistryRepository.save(new IncomeRegistry(accounting.getId(), 1, 10.0, Instant.now(), "in"));
                expenseRegistryRepository.save(new ExpenseRegistry(accounting.getId(), 1, "out", Instant.now(), 5.0));
            }
        }
    }

    @Test
    @WithMockUser
    void listEmbedsRegistriesWithBoundedQueryCount() throws Exception {
        Statistics statistics = entityManagerFactory.unwrap(SessionFactory.class).getStatistics();
        statistics.clear();

        mockMvc.perform(get("/jiltsa/api/v1/accounts/{branchId}", BRANCH_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(ACCOUNTINGS))
                .andExpect(jsonPath("$[0].incomeRegistries.length()").value(2))
                .andExpect(jsonPath("$[0].expenseRegistries.length()").value(2));

        // 1 for the accountings + 1 batched select per collection, regardless of row count
        assertThat(statistics.getPrepareStatementCount()).isLessThanOrEqualTo(3);
    }

    @Test
    @WithMockUser
    void legacyPagingParametersAreHonoured() throws Exception {
        mockMvc.perform(get("/jiltsa/api/v1/accounts/range")
                        .param("initial", LocalDateTime.now().minusDays(1).toString())
                        .param("end", LocalDateTime.now().plusDays(1).toString())
                        .param("branchId", String.valueOf(BRANCH_ID))
                        .param("page", "0").param("elements", "2")
                        .param("sortBy", "date").param("sortDirection", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(ACCOUNTINGS))
                .andExpect(jsonPath("$.pageable.pageSize").value(2))
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].date").value(greaterThan(dateOf(1))));
    }

    @Test
    @WithMockUser
    void pagingDefaultsToTwelveAscendingByDate() throws Exception {
        mockMvc.perform(get("/jiltsa/api/v1/accounts/range")
                        .param("initial", LocalDateTime.now().minusDays(1).toString())
                        .param("end", LocalDateTime.now().plusDays(1).toString())
                        .param("branchId", String.valueOf(BRANCH_ID)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable.pageSize").value(12))
                .andExpect(jsonPath("$.content[0].date").value(lessThan(dateOf(1))));
    }

    /** ISO date of the second seeded accounting; dates serialize as ISO strings so they compare lexically. */
    private String dateOf(int index) throws Exception {
        String body = mockMvc.perform(get("/jiltsa/api/v1/accounts/range")
                        .param("initial", LocalDateTime.now().minusDays(1).toString())
                        .param("end", LocalDateTime.now().plusDays(1).toString())
                        .param("branchId", String.valueOf(BRANCH_ID))
                        .param("elements", "10"))
                .andReturn().getResponse().getContentAsString();
        return com.jayway.jsonpath.JsonPath.read(body, "$.content[" + index + "].date");
    }
}
