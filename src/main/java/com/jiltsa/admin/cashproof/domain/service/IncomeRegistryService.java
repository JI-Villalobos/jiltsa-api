package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.CreateIncomeRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.IncomeRegistryDto;
import com.jiltsa.admin.cashproof.domain.repository.IncomeRegistryDRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeRegistryService {
    private final IncomeRegistryDRepository incomeRegistryDRepository;

    public List<IncomeRegistryDto> getIncomeRegistries(Integer accountingId){
        return incomeRegistryDRepository.getIncomeRegistries(accountingId);
    }

    public CreateIncomeRegistryDto createIncomeRegistry(CreateIncomeRegistryDto createIncomeRegistryDto){
        return incomeRegistryDRepository.createIncomeRegistry(createIncomeRegistryDto);
    }

    public CreateIncomeRegistryDto updateIncomeRegistry(CreateIncomeRegistryDto createIncomeRegistryDto, Integer incomeRegistryId){
        return incomeRegistryDRepository.updateIncomeRegistry(createIncomeRegistryDto, incomeRegistryId);
    }

    @Transactional
    public List<IncomeRegistryDto> createIncomesRegistry(List<CreateIncomeRegistryDto> incomes){
        return incomeRegistryDRepository.createIncomesRegistry(incomes);
    }
}
