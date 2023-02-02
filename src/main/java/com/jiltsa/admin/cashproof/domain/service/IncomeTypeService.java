package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.IncomeTypeDto;
import com.jiltsa.admin.cashproof.domain.repository.IncomeTypeDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeTypeService {
    private final IncomeTypeDRepository incomeTypeDRepository;

    public List<IncomeTypeDto> getIncomeTypes(){
        return incomeTypeDRepository.getIncomeTypes();
    }

    public IncomeTypeDto createIncomeType(IncomeTypeDto incomeTypeDto){
        return incomeTypeDRepository.createIncomeType(incomeTypeDto);
    }
}
