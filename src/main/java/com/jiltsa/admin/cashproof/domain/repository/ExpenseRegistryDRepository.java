package com.jiltsa.admin.cashproof.domain.repository;

import com.jiltsa.admin.cashproof.domain.dto.CreateExpenseRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.ExpenseRegistryDto;

import java.util.List;

public interface ExpenseRegistryDRepository {
    List<ExpenseRegistryDto> getExpenseRegistries(Integer accountingId);
    CreateExpenseRegistryDto createExpenseRegistry(CreateExpenseRegistryDto createExpenseRegistryDto);
}
