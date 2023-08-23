package com.jiltsa.admin.seller.persistence.repository;

import com.jiltsa.admin.seller.persistence.entity.Seller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Testcontainers
@ActiveProfiles("test")
class SellerRepositoryTest {
    @Autowired
    private SellerRepository sellerRepositoryUnderTest;

    @BeforeEach
    void setUp() {
        Seller seller = new Seller("Diana Path", 1, "1234", true);
        Seller seller2 = new Seller("Diana Path", 1, "1234", false);
        Seller seller3 = new Seller("Diana Path", 1, "1234", true);

        sellerRepositoryUnderTest.save(seller);
        sellerRepositoryUnderTest.save(seller2);
        sellerRepositoryUnderTest.save(seller3);
    }
    @Test
    void shouldReturnAListOfSellers() {
        //when
        List<Seller> sellers = sellerRepositoryUnderTest.findByIsActiveTrue();

        //then
        assertThat(sellers).isInstanceOf(ArrayList.class);
        assertThat(sellers.size()).isEqualTo(6);
    }
}