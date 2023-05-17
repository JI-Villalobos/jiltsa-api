package com.jiltsa.admin.seller.persistence.repository;

import com.jiltsa.admin.branch.persistence.entity.Branch;
import com.jiltsa.admin.branch.persistence.repository.BranchRepository;
import com.jiltsa.admin.cashproof.persistence.entity.Accounting;
import com.jiltsa.admin.seller.persistence.entity.Seller;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SellerRepositoryTest {
    @Autowired
    private SellerRepository sellerRepositoryUnderTest;
    @Autowired
    private BranchRepository branchRepository;
    @BeforeEach
    void setUp() {
        List<Seller> sellers = new ArrayList<>();
        List<Accounting> accountingList = new ArrayList<>();
        Branch branch = new Branch(1, "CENTRO", true, sellers, accountingList);
        branchRepository.save(branch);

        Seller seller = new Seller(1, "Diana Path", 1, "1234", true, branch, accountingList);
        Seller seller2 = new Seller(2, "Diana Path", 1, "1234", false, branch, accountingList);
        Seller seller3 = new Seller(3, "Diana Path", 1, "1234", true, branch, accountingList);

        sellerRepositoryUnderTest.save(seller);
        sellerRepositoryUnderTest.save(seller2);
        sellerRepositoryUnderTest.save(seller3);
    }

    @AfterEach
    void tearDown() {
        sellerRepositoryUnderTest.deleteAll();
    }

    @Test
    void shouldReturnAListOfSellers() {
        //when
        List<Seller> sellers = sellerRepositoryUnderTest.findByIsActiveTrue();

        //then
        assertThat(sellers).isInstanceOf(ArrayList.class);
        assertThat(sellers.size()).isEqualTo(2);
    }
}