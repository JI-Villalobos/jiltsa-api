package com.jiltsa.admin.branch.persistence.repository;

import com.jiltsa.admin.branch.persistence.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
    /** Initial balance + incomes - expenses - withdrawals; null when the branch has no configuration. */
    @Query(value = """
            SELECT bc.initial_balance
                + (SELECT COALESCE(SUM(cr.amount), 0) FROM cash_registry cr
                   JOIN accounts a ON a.id = cr.accounting_id WHERE a.branch_id = :branchId)
                - (SELECT COALESCE(SUM(er.amount), 0) FROM expense_registry er
                   JOIN accounts a ON a.id = er.accounting_id WHERE a.branch_id = :branchId)
                - (SELECT COALESCE(SUM(cw.amount), 0) FROM cash_withdrawals cw WHERE cw.branch_id = :branchId)
            FROM branch_config bc
            WHERE bc.branch_id = :branchId""", nativeQuery = true)
    Double getTotalBalance(@Param("branchId") Integer branchId);
}
