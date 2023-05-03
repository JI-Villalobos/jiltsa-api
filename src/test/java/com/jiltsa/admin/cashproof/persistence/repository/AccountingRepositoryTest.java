package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.branch.persistence.entity.Branch;
import com.jiltsa.admin.branch.persistence.repository.BranchRepository;
import com.jiltsa.admin.cashproof.persistence.entity.Accounting;
import com.jiltsa.admin.seller.persistence.entity.Seller;
import com.jiltsa.admin.seller.persistence.repository.SellerRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountingRepositoryTest {
    @Autowired
    private AccountingRepository accountingRepositoryUnderTest;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @BeforeAll
    void setup() {
        List<Accounting> accountingList = new ArrayList<>();
        Branch branch = new Branch(1, "none", true, null, null);
        branchRepository.save(branch);
        Branch branch2 = new Branch(2, "none", true, null, null);
        branchRepository.save(branch2);

        Seller seller = new Seller(1, "Diana Path", 1, "1234", true, null, null);
        sellerRepository.save(seller);

        Accounting accounting = new Accounting(1, 1, 1, LocalDateTime.now().plusHours(1), null, null, null, null);
        Accounting accounting2 = new Accounting(2, 1, 1, LocalDateTime.now().plusMonths(4), null, null, null, null);
        Accounting accounting3 = new Accounting(3, 1, 1, LocalDateTime.now().plusMonths(4), null, null, null, null);
        Accounting accounting4 = new Accounting(4, 1, 1, LocalDateTime.now().plusMonths(4), null, null, null, null);
        Accounting accounting5 = new Accounting(5, 1, 1, LocalDateTime.now().plusHours(2), null, null, null, null);
        Accounting accounting6 = new Accounting(6, 1, 1, LocalDateTime.now().plusHours(3), null, null, null, null);
        Accounting accounting7 = new Accounting(7, 1, 1, LocalDateTime.now(), null, null, null, null);
        Accounting accounting8 = new Accounting(8, 1, 1, LocalDateTime.now().plusHours(1), null, null, null, null);
        Accounting accounting9 = new Accounting(9, 1, 1, LocalDateTime.now().plusMonths(4), null, null, null, null);
        Accounting accounting10 = new Accounting(10, 1, 1, LocalDateTime.now().plusMonths(4), null, null, null, null);
        Accounting accounting11 = new Accounting(11, 1, 1, LocalDateTime.now().plusMonths(4), null, null, null, null);
        Accounting accounting12 = new Accounting(12, 1, 1, LocalDateTime.now().plusHours(2), null, null, null, null);
        Accounting accounting13 = new Accounting(13, 1, 1, LocalDateTime.now().plusHours(3), null, null, null, null);
        Accounting accounting14 = new Accounting(14, 1, 1, LocalDateTime.now(), null, null, null, null);
        Accounting accounting15 = new Accounting(15, 1, 1, LocalDateTime.now().plusHours(1), null, null, null, null);
        Accounting accounting16 = new Accounting(16, 1, 1, LocalDateTime.now().plusMonths(4), null, null, null, null);
        Accounting accounting17 = new Accounting(17, 1, 1, LocalDateTime.now().plusMonths(4), null, null, null, null);
        Accounting accounting18 = new Accounting(18, 1, 1, LocalDateTime.now().plusMonths(4), null, null, null, null);
        Accounting accounting19 = new Accounting(19, 1, 1, LocalDateTime.now().plusHours(2), null, null, null, null);
        Accounting accounting20 = new Accounting(20, 1, 1, LocalDateTime.now().plusHours(3), null, null, null, null);
        Accounting accounting21 = new Accounting(21, 1, 1, LocalDateTime.now(), null, null, null, null);
        Accounting accounting22 = new Accounting(22, 1, 2, LocalDateTime.now().plusHours(2), null, null, null, null);
        Accounting accounting23 = new Accounting(23, 1, 2, LocalDateTime.now().plusHours(3), null, null, null, null);
        Accounting accounting24 = new Accounting(24, 1, 2, LocalDateTime.now(), null, null, null, null);


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
        List<Accounting> accountingList = accountingRepositoryUnderTest.findTop20ByBranchId(1);

        //then
        assertThat(accountingList).isInstanceOf(ArrayList.class);
        assertThat(accountingList.size()).isEqualTo(20);
    }

    @Test
    void shouldFindByAccountingListBetweenTwoDates() {
        LocalDateTime start = LocalDateTime.now().minusMonths(1);
        LocalDateTime end = LocalDateTime.now().plusMonths(1);

        //when
        List<Accounting> accountingList = accountingRepositoryUnderTest.findByDateBetweenAndBranchId(start, end, 1);

        //then
        assertThat(accountingList).isInstanceOf(ArrayList.class);
        assertThat(accountingList.size()).isEqualTo(12);
    }
}