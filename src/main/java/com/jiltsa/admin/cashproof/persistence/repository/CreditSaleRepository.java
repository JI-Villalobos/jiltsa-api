package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.persistence.entity.CreditSale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditSaleRepository extends JpaRepository<CreditSale, Integer> {
    List<CreditSale> findByBranchId(Integer branchId);
    List<CreditSale> findByBranchIdAndIsPaidTrue(Integer branchId);
    List<CreditSale> findByBranchIdAndIsPaidFalse(Integer branchId);
}
