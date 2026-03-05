package com.jiltsa.admin.operativity.domain.repository;

import com.jiltsa.admin.operativity.domain.dto.OperativeExpenseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface OperativeExpenseDRepository {
    List<OperativeExpenseDto> getOperativeExpensesByBranch(Integer branchId, LocalDateTime date);
    OperativeExpenseDto saveOperativeExpense(OperativeExpenseDto operativeExpenseDto);
    void deleteOperativeExpense(OperativeExpenseDto operativeExpenseDto);
}
