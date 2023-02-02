package com.jiltsa.admin.cashproof.domain.repository;

import com.jiltsa.admin.cashproof.domain.dto.ExpenseTypeDto;

import java.util.List;

public interface ExpenseTypeDRepository {
    List<ExpenseTypeDto> getExpenseTypes();
    ExpenseTypeDto createExpenseType();
}
