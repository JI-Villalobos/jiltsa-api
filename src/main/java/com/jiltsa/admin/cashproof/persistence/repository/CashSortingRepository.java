package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.persistence.entity.CashSorting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CashSortingRepository extends JpaRepository<CashSorting, Integer> {
    Optional<CashSorting> findByAccountingId(Integer accountingId);
}
