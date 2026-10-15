package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.CreateIncomeRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.IncomeRegistryDto;
import com.jiltsa.admin.cashproof.persistence.entity.IncomeRegistry;
import com.jiltsa.admin.cashproof.persistence.mapper.IncomeRegistryMapper;
import com.jiltsa.admin.cashproof.persistence.repository.IncomeRegistryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IncomeRegistryService {
    private final IncomeRegistryRepository repository;
    private final IncomeRegistryMapper mapper;

    public List<IncomeRegistryDto> getIncomeRegistries(Integer accountingId) {
        return mapper.toIncomeRegistryDtoList(repository.findByAccountingId(accountingId));
    }

    @Transactional
    public CreateIncomeRegistryDto createIncomeRegistry(CreateIncomeRegistryDto createIncomeRegistryDto) {
        IncomeRegistry incomeRegistry = mapper.toIncomeRegistry(createIncomeRegistryDto);
        return mapper.toCreateIncomeRegistryDto(repository.save(incomeRegistry));
    }

    @Transactional
    public IncomeRegistryDto updateIncomeRegistry(IncomeRegistryDto incomeRegistryDto) {
        IncomeRegistry incomeRegistry = mapper.toIncomeRegistry(incomeRegistryDto);
        return mapper.toIncomeRegistryDto(repository.save(incomeRegistry));
    }

    @Transactional
    public void deleteIncomeRegistry(Integer incomeRegistryId) {
        repository.deleteById(incomeRegistryId);
    }

    @Transactional
    public List<IncomeRegistryDto> createIncomesRegistry(List<CreateIncomeRegistryDto> incomes) {
        List<IncomeRegistry> incomeRegistries = mapper.toIncomeRegistryList(incomes);

        return mapper.toIncomeRegistryDtoList(repository.saveAll(incomeRegistries));
    }
}
