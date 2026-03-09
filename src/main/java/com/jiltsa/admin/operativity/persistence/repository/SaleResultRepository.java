package com.jiltsa.admin.operativity.persistence.repository;

import com.jiltsa.admin.operativity.persistence.entity.SaleResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleResultRepository extends JpaRepository<SaleResult, Integer> {
    List<SaleResult> findByBranchIdAndDateAfter(Integer branchId, LocalDateTime date);
    List<SaleResult> findByBranchIdAndDateBetween(Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate);
}
