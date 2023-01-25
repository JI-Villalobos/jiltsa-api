package com.jiltsa.admin.seller.persistence.repository;

import com.jiltsa.admin.seller.persistence.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Integer> {
    List<Seller> findByIsActiveTrue();
}
