package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.ExpenseTypeDto;
import com.jiltsa.admin.cashproof.persistence.entity.ExpenseType;
import com.jiltsa.admin.cashproof.persistence.mapper.ExpenseTypeMapper;
import com.jiltsa.admin.cashproof.persistence.repository.ExpenseTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExpenseTypeService {
    private final ExpenseTypeRepository repository;
    private final ExpenseTypeMapper mapper;

    public List<ExpenseTypeDto> getExpenseTypes() {
        return mapper.toExpenseTypeDtoList(repository.findAll());
    }

    @Transactional
    public ExpenseTypeDto createExpenseType(ExpenseTypeDto expenseTypeDto) {
        ExpenseType expenseType = mapper.toExpenseType(expenseTypeDto);
        return mapper.toExpenseTypeDto(repository.save(expenseType));
    }
}
