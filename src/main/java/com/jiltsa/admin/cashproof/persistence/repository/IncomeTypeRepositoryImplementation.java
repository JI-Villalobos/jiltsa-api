package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.domain.dto.IncomeTypeDto;
import com.jiltsa.admin.cashproof.domain.repository.IncomeTypeDRepository;
import com.jiltsa.admin.cashproof.persistence.entity.IncomeType;
import com.jiltsa.admin.cashproof.persistence.mapper.IncomeTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class IncomeTypeRepositoryImplementation implements IncomeTypeDRepository {
    private final IncomeTypeRepository repository;
    private final IncomeTypeMapper mapper;
    @Override
    public List<IncomeTypeDto> getIncomeTypes() {
        return mapper.incomeTypeDtoList(repository.findAll());
    }

    @Override
    public IncomeTypeDto createIncomeType(IncomeTypeDto incomeTypeDto) {
        IncomeType incomeType = mapper.toIncomeType(incomeTypeDto);
        return mapper.toIncomeTypeDto(repository.save(incomeType));
    }
}
