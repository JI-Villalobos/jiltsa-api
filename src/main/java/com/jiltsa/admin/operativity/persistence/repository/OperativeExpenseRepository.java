package com.jiltsa.admin.operativity.persistence.repository;

import com.jiltsa.admin.operativity.persistence.entity.OperativeExpense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OperativeExpenseRepository  extends JpaRepository<OperativeExpense, Integer> {
    List<OperativeExpense> findByBranchIdAndExpenseDateAfter(Integer branchId, LocalDateTime date);
}
