package com.jiltsa.admin.cashproof.domain.repository;

import com.jiltsa.admin.cashproof.domain.dto.CreateIncomeRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.IncomeRegistryDto;

import java.util.List;

public interface IncomeRegistryDRepository {
    List<IncomeRegistryDto> getIncomeRegistries(Integer accountingId);
    CreateIncomeRegistryDto createIncomeRegistry(CreateIncomeRegistryDto createIncomeRegistryDto);
    CreateIncomeRegistryDto updateIncomeRegistry(CreateIncomeRegistryDto createIncomeRegistryDto, Integer incomeRegistryId);
    List<IncomeRegistryDto> createIncomesRegistry(List<CreateIncomeRegistryDto> incomes);
}
