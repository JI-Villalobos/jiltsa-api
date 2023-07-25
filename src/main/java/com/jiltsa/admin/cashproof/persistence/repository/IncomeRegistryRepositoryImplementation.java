package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.domain.dto.CreateExpenseRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateIncomeRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.IncomeRegistryDto;
import com.jiltsa.admin.cashproof.domain.repository.IncomeRegistryDRepository;
import com.jiltsa.admin.cashproof.persistence.entity.IncomeRegistry;
import com.jiltsa.admin.cashproof.persistence.mapper.IncomeRegistryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RequiredArgsConstructor
public class IncomeRegistryRepositoryImplementation implements IncomeRegistryDRepository {
    private final IncomeRegistryRepository repository;
    private final IncomeRegistryMapper mapper;
    @Override
    public List<IncomeRegistryDto> getIncomeRegistries(Integer accountingId) {
        return mapper.toIncomeRegistryDtoList(repository.findByAccountingId(accountingId));
    }

    @Override
    public CreateIncomeRegistryDto createIncomeRegistry(CreateIncomeRegistryDto createIncomeRegistryDto) {
        IncomeRegistry incomeRegistry = mapper.toIncomeRegistry(createIncomeRegistryDto);
        return mapper.toCreateIncomeRegistryDto(repository.save(incomeRegistry));
    }

    @Override
    public CreateIncomeRegistryDto updateIncomeRegistry(CreateIncomeRegistryDto createIncomeRegistryDto, Integer incomeRegistryId) {
        IncomeRegistry incomeRegistry = mapper.toIncomeRegistry(createIncomeRegistryDto);
        return mapper.toCreateIncomeRegistryDto(repository.save(incomeRegistry));
    }
}
