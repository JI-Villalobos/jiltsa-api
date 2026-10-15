package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.branch.persistence.entity.Branch;
import com.jiltsa.admin.branch.persistence.repository.BranchRepository;
import com.jiltsa.admin.cashproof.persistence.entity.CashWithdrawal;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class CashWithdrawalRepositoryTest {
    @Autowired
    CashWithdrawalRepository repositoryUnderTest;
    @Autowired
    BranchRepository branchRepository;

    private Integer nazas;

   @BeforeAll
    void setUp() {
        nazas = branchRepository.save(new Branch("Nazas", true)).getId();
        Integer pen = branchRepository.save(new Branch("Pen", true)).getId();
        repositoryUnderTest.save(new CashWithdrawal(LocalDateTime.now(), 234.00, "OXXO", nazas, "Diana"));
        repositoryUnderTest.save(new CashWithdrawal(LocalDateTime.now(), 234.00, "OXXO", nazas, "Maria"));
        repositoryUnderTest.save(new CashWithdrawal(LocalDateTime.now(), 234.00, "OXXO", pen, "Gina"));
    }
    @Test
    void shouldFindAWithdrawalListByBranch() {
        //when
        List<CashWithdrawal> cashWithdrawalList = repositoryUnderTest.findByBranchId(nazas);

        //then
        assertThat(cashWithdrawalList).extracting(CashWithdrawal::getSellerName).containsExactlyInAnyOrder("Diana", "Maria");
    }
}
