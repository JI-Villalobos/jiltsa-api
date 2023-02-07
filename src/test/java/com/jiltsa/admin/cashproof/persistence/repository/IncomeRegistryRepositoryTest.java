package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.branch.persistence.entity.Branch;
import com.jiltsa.admin.branch.persistence.repository.BranchRepository;
import com.jiltsa.admin.cashproof.persistence.entity.Accounting;
import com.jiltsa.admin.cashproof.persistence.entity.IncomeRegistry;
import com.jiltsa.admin.cashproof.persistence.entity.IncomeType;
import com.jiltsa.admin.seller.persistence.entity.Seller;
import com.jiltsa.admin.seller.persistence.repository.SellerRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IncomeRegistryRepositoryTest {
    @Autowired
    IncomeRegistryRepository repositoryUnderTest;
    @Autowired
    AccountingRepository accountingRepository;
    @Autowired
    IncomeTypeRepository incomeTypeRepository;
    @Autowired
    BranchRepository branchRepository;
    @Autowired
    SellerRepository sellerRepository;

    @BeforeAll
    void setUp() {
        List<IncomeRegistry> incomeRegistryList = new ArrayList<>();
        Branch branch = new Branch(1, "nazas", true, null, null);
        branchRepository.save(branch);

        Seller seller = new Seller(1, "Seller name", 1, null, true, null, null);
        sellerRepository.save(seller);

        Accounting accounting = new Accounting(1, 1, 1, LocalDateTime.now(), null, null, null, null);
        Accounting accounting2 = new Accounting(2, 1, 1, LocalDateTime.now(), null, null, null, null);
        Accounting accounting3 = new Accounting(3, 1, 1, LocalDateTime.now(), null, null, null, null);
        accountingRepository.save(accounting);
        accountingRepository.save(accounting2);
        accountingRepository.save(accounting3);

        IncomeType incomeType = new IncomeType(1, "other", null);
        incomeTypeRepository.save(incomeType);

        IncomeRegistry incomeRegistry = new IncomeRegistry(1, 1, 1, 465.45, Instant.now(), "Income tag", null, null);
        IncomeRegistry incomeRegistry2 = new IncomeRegistry(2, 1, 1, 465.45, Instant.now(), "Income tag", null, null);
        IncomeRegistry incomeRegistry3 = new IncomeRegistry(3, 2, 1, 465.45, Instant.now(), "Income tag", null, null);
        IncomeRegistry incomeRegistry4 = new IncomeRegistry(4, 3, 1, 465.45, Instant.now(), "Income tag", null, null);

        incomeRegistryList.add(incomeRegistry);
        incomeRegistryList.add(incomeRegistry2);
        incomeRegistryList.add(incomeRegistry3);
        incomeRegistryList.add(incomeRegistry4);

        incomeRegistryList.forEach(income -> repositoryUnderTest.save(income));
    }

    @Test
    void shouldFindIncomeRegistriesByAccountingId() {
        //when
        List<IncomeRegistry> incomeRegistryList = repositoryUnderTest.findByAccountingId(1);

        //then
        assertThat(incomeRegistryList).isInstanceOf(ArrayList.class);
        assertThat(incomeRegistryList.size()).isEqualTo(2);
    }
}