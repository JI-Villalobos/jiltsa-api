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

    @Query(value = "CALL sp_avg_sales(:p_branch_id)", nativeQuery = true)
    Double getAverageSales(
            @Param("p_branch_id") Integer branchId
    );
}
