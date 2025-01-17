package com.jiltsa.admin.cashproof.domain.repository;

import com.jiltsa.admin.cashproof.domain.dto.CreateIncomeRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.IncomeRegistryDto;

import java.util.List;

public interface IncomeRegistryDRepository {
    List<IncomeRegistryDto> getIncomeRegistries(Integer accountingId);
    CreateIncomeRegistryDto createIncomeRegistry(CreateIncomeRegistryDto createIncomeRegistryDto);
    IncomeRegistryDto updateIncomeRegistry(IncomeRegistryDto createIncomeRegistryDto);
    List<IncomeRegistryDto> createIncomesRegistry(List<CreateIncomeRegistryDto> incomes);
    void deleteIncomeRegistry(Integer incomeRegistryId);
}
