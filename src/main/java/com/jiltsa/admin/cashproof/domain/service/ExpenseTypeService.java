package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.ExpenseTypeDto;
import com.jiltsa.admin.cashproof.domain.repository.ExpenseTypeDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExpenseTypeService {
    private final ExpenseTypeDRepository expenseTypeDRepository;

    public List<ExpenseTypeDto> getExpenseTypes(){
        return expenseTypeDRepository.getExpenseTypes();
    }

    @Transactional
    public ExpenseTypeDto createExpenseType(ExpenseTypeDto expenseTypeDto){
        return expenseTypeDRepository.createExpenseType(expenseTypeDto);
    }
}
