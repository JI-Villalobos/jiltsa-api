package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.CreateExpenseRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.ExpenseRegistryDto;
import com.jiltsa.admin.cashproof.domain.repository.ExpenseRegistryDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseRegistryService {
    private final ExpenseRegistryDRepository expenseRegistryDRepository;

    public List<ExpenseRegistryDto> getExpenseRegistries(Integer accountingId){
        return expenseRegistryDRepository.getExpenseRegistries(accountingId);
    }

    public CreateExpenseRegistryDto createExpenseRegistry(CreateExpenseRegistryDto createExpenseRegistryDto){
        return expenseRegistryDRepository.createExpenseRegistry(createExpenseRegistryDto);
    }
}
