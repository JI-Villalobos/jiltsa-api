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

    /** Operative expenses plus the branch's own expense registries, each rounded to cents. */
    @Query(value = """
            SELECT CAST((SELECT COALESCE(SUM(oe.amount), 0) FROM operative_expense oe
                         WHERE oe.branch_id = :branchId AND oe.expense_date BETWEEN :initialDate AND :finalDate)
                        AS DECIMAL(10, 2))
                 + CAST((SELECT COALESCE(SUM(er.amount), 0) FROM expense_registry er
                         JOIN accounts a ON a.id = er.accounting_id
                         WHERE a.branch_id = :branchId AND a.date BETWEEN :initialDate AND :finalDate)
                        AS DECIMAL(10, 2))""", nativeQuery = true)
    Double getTotalExpenses(
            @Param("branchId") Integer branchId,
            @Param("initialDate") LocalDateTime initialDate,
            @Param("finalDate") LocalDateTime finalDate
    );
}
