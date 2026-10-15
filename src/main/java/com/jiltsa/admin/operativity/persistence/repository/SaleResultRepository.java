package com.jiltsa.admin.operativity.persistence.repository;

import com.jiltsa.admin.operativity.persistence.entity.SaleResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleResultRepository extends JpaRepository<SaleResult, Integer> {
    List<SaleResult> findByBranchIdAndInitialDateAfter(Integer branchId, LocalDateTime date);
    List<SaleResult> findByBranchIdAndInitialDateBetween(Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate);

    @Query("SELECT COALESCE(AVG(s.pharmacyAmount), 0.0) FROM SaleResult s WHERE s.branchId = :branchId")
    Double getAverageSales(@Param("branchId") Integer branchId);
}
