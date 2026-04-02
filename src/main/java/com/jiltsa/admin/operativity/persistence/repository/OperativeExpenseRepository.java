package com.jiltsa.admin.operativity.persistence.repository;

import com.jiltsa.admin.operativity.persistence.entity.OperativeExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OperativeExpenseRepository  extends JpaRepository<OperativeExpense, Integer> {
    List<OperativeExpense> findByBranchIdAndExpenseDateAfter(Integer branchId, LocalDateTime date);
    List<OperativeExpense> findByBranchIdAndExpenseDateBetween(Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate);

    //, outputParameterName = "total_expenses"
    @Query(value = "CALL sp_total_expenses(:p_branch_id, :p_fecha_inicio, :p_fecha_fin)", nativeQuery = true)
    Double getTotalExpenses(
            @Param("p_branch_id") Integer branchId,
            @Param("p_fecha_inicio") LocalDateTime initialDate,
            @Param("p_fecha_fin") LocalDateTime finalDate
    );
}
