package com.jiltsa.admin.cashproof.domain.repository;

import com.jiltsa.admin.cashproof.domain.dto.CreateExpenseRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.ExpenseReportDto;
import com.jiltsa.admin.cashproof.domain.dto.ExpenseRegistryDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseRegistryDRepository {
    List<ExpenseRegistryDto> getExpenseRegistries(Integer accountingId);
    CreateExpenseRegistryDto createExpenseRegistry(CreateExpenseRegistryDto createExpenseRegistryDto);
    ExpenseRegistryDto updateExpenseRegistry(ExpenseRegistryDto expenseRegistryDto);
    void deleteExpenseRegistry(Integer expenseRegistryId);
    List<ExpenseReportDto> getExpenseReport(Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate);
    List<ExpenseReportDto> getPharmacyExpenseReport(Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate);
}
