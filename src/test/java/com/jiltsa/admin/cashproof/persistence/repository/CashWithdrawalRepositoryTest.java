package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.persistence.entity.CashWithdrawal;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class CashWithdrawalRepositoryTest {
    @Autowired
    CashWithdrawalRepository repositoryUnderTest;

   @BeforeAll
    void setUp() {
        CashWithdrawal cashWithdrawal = new CashWithdrawal(
                LocalDateTime.now(), 234.00, "OXXO", "Nazas", "Diana"
        );
        repositoryUnderTest.save(cashWithdrawal);
        CashWithdrawal cashWithdrawal2 = new CashWithdrawal(
                LocalDateTime.now(), 234.00, "OXXO", "Nazas", "Maria"
        );
        repositoryUnderTest.save(cashWithdrawal2);
        CashWithdrawal cashWithdrawal3 = new CashWithdrawal(
                LocalDateTime.now(), 234.00, "OXXO", "Pen", "Gina"
        );
        repositoryUnderTest.save(cashWithdrawal3);
    }
    @Test
    void shouldFindAWithdrawalListByBranch() {
        //when
        List<CashWithdrawal> cashWithdrawalList = repositoryUnderTest.findByBranch("Nazas");

        //then
        assertThat(cashWithdrawalList).isInstanceOf(ArrayList.class);
        assertThat(cashWithdrawalList.size()).isEqualTo(2);

    }
}