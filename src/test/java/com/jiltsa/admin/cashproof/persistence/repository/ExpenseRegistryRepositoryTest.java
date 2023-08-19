package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.persistence.entity.ExpenseRegistry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExpenseRegistryRepositoryTest {
    @Autowired
    ExpenseRegistryRepository expenseRegistryRepositoryUnderTest;

    @BeforeAll
    void setUp() {
        List<ExpenseRegistry> expenseRegistryList = new ArrayList<>();

        ExpenseRegistry expenseRegistry = new ExpenseRegistry(1, 1, "Expense description", Instant.now(), 456.8);
        ExpenseRegistry expenseRegistry2 = new ExpenseRegistry(2, 1, "Expense description", Instant.now(), 456.8);
        ExpenseRegistry expenseRegistry3 = new ExpenseRegistry(3, 1, "Expense description", Instant.now(), 456.8);
        ExpenseRegistry expenseRegistry4 = new ExpenseRegistry(1, 1, "Expense description", Instant.now(), 456.8);
        ExpenseRegistry expenseRegistry5 = new ExpenseRegistry(1, 1, "Expense description", Instant.now(), 456.8);

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