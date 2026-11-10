package com.jiltsa.admin.seller.persistence.repository;

import com.jiltsa.admin.seller.persistence.entity.SellerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SellerProfileRepository extends JpaRepository<SellerProfile, Integer> {
    Optional<SellerProfile> findBySellerId(Integer sellerId);
    List<SellerProfile> findBySellerBranchIdOrderByFullNameAsc(Integer branchId);
}
