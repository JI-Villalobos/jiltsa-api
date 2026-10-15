package com.jiltsa.admin.operativity.persistence.repository;

import com.jiltsa.admin.operativity.persistence.entity.SaleResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/** getAverageSales replaced the sp_avg_sales procedure and must return what it returned. */
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class SaleResultRepositoryTest {
    @Autowired
    SaleResultRepository repositoryUnderTest;
    @Autowired
    JdbcTemplate jdbc;

    private int branch;
    private int branchWithoutSales;

    @BeforeAll
    void setUp() {
        branch = branch("Coyote");
        int otherBranch = branch("Nazas");
        branchWithoutSales = branch("Pen");
        save(branch, 100.0);
        save(branch, 250.0);
        save(otherBranch, 999.0);
    }

    @Test
    void averagesTheBranchsPharmacySales() {
        assertThat(repositoryUnderTest.getAverageSales(branch)).isEqualTo(175.0);
    }

    @Test
    void aBranchWithoutSalesAveragesZero() {
        assertThat(repositoryUnderTest.getAverageSales(branchWithoutSales)).isEqualTo(0.0);
    }

    private int branch(String name) {
        return new SimpleJdbcInsert(jdbc).withTableName("branches").usingGeneratedKeyColumns("id")
                .executeAndReturnKey(Map.of("name", name, "is_active", true)).intValue();
    }

    private void save(int branchId, double pharmacyAmount) {
        LocalDateTime now = LocalDateTime.now();
        repositoryUnderTest.save(new SaleResult(null, pharmacyAmount, 0.0, 1, 2026, now, now, branchId));
    }
}
