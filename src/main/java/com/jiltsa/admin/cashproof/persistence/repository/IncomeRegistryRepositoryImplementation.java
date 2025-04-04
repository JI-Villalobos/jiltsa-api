package com.jiltsa.admin.cashproof.persistence.repository;

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
    public IncomeRegistryDto updateIncomeRegistry(IncomeRegistryDto incomeRegistryDto) {
        IncomeRegistry incomeRegistry = mapper.toIncomeRegistry(incomeRegistryDto);
        return mapper.toIncomeRegistryDto(repository.save(incomeRegistry));
    }

    @Override
    public List<IncomeRegistryDto> createIncomesRegistry(List<CreateIncomeRegistryDto> incomes) {
        List<IncomeRegistry> incomeRegistries = mapper.toIncomeRegistryList(incomes);

        return mapper.toIncomeRegistryDtoList(repository.saveAll(incomeRegistries));
    }

    @Override
    public void deleteIncomeRegistry(Integer incomeRegistryId) {
        repository.deleteById(incomeRegistryId);
    }
}
