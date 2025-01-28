package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.persistence.entity.Accounting;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Testcontainers
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountingRepositoryTest {
    @Autowired
    private AccountingRepository accountingRepositoryUnderTest;

    @BeforeAll
    void setup() {
        List<Accounting> accountingList = new ArrayList<>();

        Accounting accounting = new Accounting(1, 1, LocalDateTime.now().plusHours(1));
        Accounting accounting2 = new Accounting(1, 1, LocalDateTime.now().plusMonths(4));
        Accounting accounting3 = new Accounting(1, 1, LocalDateTime.now().plusMonths(4));
        Accounting accounting4 = new Accounting(1, 1, LocalDateTime.now().plusMonths(4));
        Accounting accounting5 = new Accounting(1, 1, LocalDateTime.now().plusHours(2));
        Accounting accounting6 = new Accounting(1, 1, LocalDateTime.now().plusHours(3));
        Accounting accounting7 = new Accounting(1, 1, LocalDateTime.now());
        Accounting accounting8 = new Accounting(1, 1, LocalDateTime.now().plusHours(1));
        Accounting accounting9 = new Accounting(1, 1, LocalDateTime.now().plusMonths(4));
        Accounting accounting10 = new Accounting(1, 1, LocalDateTime.now().plusMonths(4));
        Accounting accounting11 = new Accounting(1, 1, LocalDateTime.now().plusMonths(4));
        Accounting accounting12 = new Accounting(1, 1, LocalDateTime.now().plusHours(2));
        Accounting accounting13 = new Accounting(1, 1, LocalDateTime.now().plusHours(3));
        Accounting accounting14 = new Accounting(1, 1, LocalDateTime.now());
        Accounting accounting15 = new Accounting(1, 1, LocalDateTime.now().plusHours(1));
        Accounting accounting16 = new Accounting(1, 1, LocalDateTime.now().plusMonths(4));
        Accounting accounting17 = new Accounting(1, 1, LocalDateTime.now().plusMonths(4));
        Accounting accounting18 = new Accounting(1, 1, LocalDateTime.now().plusMonths(4));
        Accounting accounting19 = new Accounting(1, 1, LocalDateTime.now().plusHours(2));
        Accounting accounting20 = new Accounting(1, 1, LocalDateTime.now().plusHours(3));
        Accounting accounting21 = new Accounting(1, 1, LocalDateTime.now());
        Accounting accounting22 = new Accounting(1, 2, LocalDateTime.now().plusHours(2));
        Accounting accounting23 = new Accounting(1, 2, LocalDateTime.now().plusHours(3));
        Accounting accounting24 = new Accounting(1, 2, LocalDateTime.now());


        accountingList.add(accounting);
        accountingList.add(accounting2);
        accountingList.add(accounting3);
        accountingList.add(accounting4);
        accountingList.add(accounting5);
        accountingList.add(accounting6);
        accountingList.add(accounting7);
        accountingList.add(accounting8);
        accountingList.add(accounting9);
        accountingList.add(accounting10);
        accountingList.add(accounting11);
        accountingList.add(accounting12);
        accountingList.add(accounting13);
        accountingList.add(accounting14);
        accountingList.add(accounting15);
        accountingList.add(accounting16);
        accountingList.add(accounting17);
        accountingList.add(accounting18);
        accountingList.add(accounting19);
        accountingList.add(accounting20);
        accountingList.add(accounting21);
        accountingList.add(accounting22);
        accountingList.add(accounting23);
        accountingList.add(accounting24);

        accountingList.forEach(acc -> accountingRepositoryUnderTest.save(acc));
    }
    @Test
    void shouldFindTop20AccountingRegistries() {
        //when
        List<Accounting> accountingList = accountingRepositoryUnderTest.findByBranchIdAndDateAfterOrderByDateAsc(1, LocalDateTime.now().minusWeeks(45));

        //then
        assertThat(accountingList).isInstanceOf(ArrayList.class);
        assertThat(accountingList.size()).isEqualTo(21);
    }

    @Test
    void shouldFindByAccountingListBetweenTwoDates() {
        LocalDateTime start = LocalDateTime.now().minusMonths(1);
        LocalDateTime end = LocalDateTime.now().plusMonths(1);

        //when
        List<Accounting> accountingList =
                accountingRepositoryUnderTest.findByDateBetweenAndBranchIdOrderByDateAsc(
                        start, end, 1
                );

        //then
        assertThat(accountingList).isInstanceOf(ArrayList.class);
        assertThat(accountingList.size()).isEqualTo(12);
    }
}