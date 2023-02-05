package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.branch.persistence.entity.Branch;
import com.jiltsa.admin.branch.persistence.repository.BranchRepository;
import com.jiltsa.admin.cashproof.persistence.entity.Accounting;
import com.jiltsa.admin.cashproof.persistence.entity.ExpenseRegistry;
import com.jiltsa.admin.cashproof.persistence.entity.ExpenseType;
import com.jiltsa.admin.seller.persistence.entity.Seller;
import com.jiltsa.admin.seller.persistence.repository.SellerRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExpenseRegistryRepositoryTest {
    @Autowired
    ExpenseRegistryRepository expenseRegistryRepositoryUnderTest;
    @Autowired
    AccountingRepository accountingRepository;
    @Autowired
    ExpenseTypeRepository expenseTypeRepository;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    SellerRepository sellerRepository;

    @BeforeAll
    void setUp() {
        List<ExpenseRegistry> expenseRegistryList = new ArrayList<>();

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

        ExpenseType expenseType = new ExpenseType(1, "other", null);
        expenseTypeRepository.save(expenseType);

        ExpenseRegistry expenseRegistry = new ExpenseRegistry(1, 1, 1, "Expense description", Instant.now(), 456.8, null, null);
        ExpenseRegistry expenseRegistry2 = new ExpenseRegistry(2, 2, 1, "Expense description", Instant.now(), 456.8, null, null);
        ExpenseRegistry expenseRegistry3 = new ExpenseRegistry(3, 3, 1, "Expense description", Instant.now(), 456.8, null, null);
        ExpenseRegistry expenseRegistry4 = new ExpenseRegistry(4, 1, 1, "Expense description", Instant.now(), 456.8, null, null);
        ExpenseRegistry expenseRegistry5 = new ExpenseRegistry(5, 1, 1, "Expense description", Instant.now(), 456.8, null, null);

        expenseRegistryList.add(expenseRegistry);
        expenseRegistryList.add(expenseRegistry2);
        expenseRegistryList.add(expenseRegistry3);
        expenseRegistryList.add(expenseRegistry4);
        expenseRegistryList.add(expenseRegistry5);

        expenseRegistryList.forEach(expense -> expenseRegistryRepositoryUnderTest.save(expense));
    }

    @Test
    void shouldReturnAListOfExpenseRegistriesByAccountingId(){
        //when
        List<ExpenseRegistry> expenseRegistryList = expenseRegistryRepositoryUnderTest.findByAccountingId(1);

        //then
        assertThat(expenseRegistryList).isInstanceOf(ArrayList.class);
        assertThat(expenseRegistryList.size()).isEqualTo(3);
    }
}