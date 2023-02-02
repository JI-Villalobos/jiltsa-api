package com.jiltsa.admin.cashproof.domain.repository;

import com.jiltsa.admin.cashproof.domain.dto.IncomeTypeDto;

import java.util.List;

public interface IncomeTypeDRepository {
    List<IncomeTypeDto> getIncomeTypes();
    IncomeTypeDto createIncomeType(IncomeTypeDto incomeTypeDto);
}
