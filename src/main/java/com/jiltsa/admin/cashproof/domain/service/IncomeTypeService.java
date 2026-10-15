package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.IncomeTypeDto;
import com.jiltsa.admin.cashproof.persistence.entity.IncomeType;
import com.jiltsa.admin.cashproof.persistence.mapper.IncomeTypeMapper;
import com.jiltsa.admin.cashproof.persistence.repository.IncomeTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IncomeTypeService {
    private final IncomeTypeRepository repository;
    private final IncomeTypeMapper mapper;

    public List<IncomeTypeDto> getIncomeTypes() {
        return mapper.toIncomeTypeDtoList(repository.findAll());
    }

    @Transactional
    public IncomeTypeDto createIncomeType(IncomeTypeDto incomeTypeDto) {
        IncomeType incomeType = mapper.toIncomeType(incomeTypeDto);
        return mapper.toIncomeTypeDto(repository.save(incomeType));
    }
}
