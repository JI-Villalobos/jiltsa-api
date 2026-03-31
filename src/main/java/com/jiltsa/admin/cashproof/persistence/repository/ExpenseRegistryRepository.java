package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.persistence.entity.ExpenseRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseRegistryRepository extends JpaRepository<ExpenseRegistry, Integer> {
    List<ExpenseRegistry> findByAccountingId(Integer accountingId);
    @Query("""
        SELECT
            et.type           AS type,
            a.branchId        AS branchId,
            er.expenseTypeId  AS expenseTypeId,
            SUM(er.amount)    AS total
        FROM ExpenseRegistry er
        JOIN er.accounting a
        JOIN er.expenseType et
        WHERE a.date BETWEEN :initial AND :end
          AND a.branchId = :branchId
        GROUP BY et.type, a.branchId, er.expenseTypeId
    """)
    List<ExpenseResult> getExpenseReport(
            @Param("branchId") Integer branchId,
            @Param("initial") LocalDateTime initialDate,
            @Param("end")      LocalDateTime finalDate
    );

    @Query("""
        SELECT
            et.type           AS type,
            a.branchId        AS branchId,
            er.expenseTypeId  AS expenseTypeId,
            SUM(er.amount)    AS total
        FROM ExpenseRegistry er
        JOIN er.accounting a
        JOIN er.expenseType et
        WHERE a.date BETWEEN :initial AND :end
          AND er.expenseTypeId = 6
          AND a.branchId = :branchId
        GROUP BY et.type, a.branchId, er.expenseTypeId
    """)
    List<ExpenseResult> getPharmacyExpenseReport(
            @Param("branchId") Integer branchId,
            @Param("initial") LocalDateTime initialDate,
            @Param("end")      LocalDateTime finalDate
    );
}
