package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.persistence.entity.IncomeRegistry;
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
class IncomeRegistryRepositoryTest {
    @Autowired
    IncomeRegistryRepository repositoryUnderTest;

    @BeforeAll
    void setUp() {
        List<IncomeRegistry> incomeRegistryList = new ArrayList<>();

        IncomeRegistry incomeRegistry = new IncomeRegistry(1, 1, 465.45, Instant.now(), "Income tag");
        IncomeRegistry incomeRegistry2 = new IncomeRegistry(1, 1, 465.45, Instant.now(), "Income tag");
        IncomeRegistry incomeRegistry3 = new IncomeRegistry(2, 1, 465.45, Instant.now(), "Income tag");
        IncomeRegistry incomeRegistry4 = new IncomeRegistry(3, 1, 465.45, Instant.now(), "Income tag");

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