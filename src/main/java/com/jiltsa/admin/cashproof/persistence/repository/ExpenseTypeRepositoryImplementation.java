package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.domain.dto.ExpenseTypeDto;
import com.jiltsa.admin.cashproof.domain.repository.ExpenseTypeDRepository;
import com.jiltsa.admin.cashproof.persistence.entity.ExpenseType;
import com.jiltsa.admin.cashproof.persistence.mapper.ExpenseTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ExpenseTypeRepositoryImplementation implements ExpenseTypeDRepository {
    private final ExpenseTypeRepository repository;
    private final ExpenseTypeMapper mapper;
    @Override
    public List<ExpenseTypeDto> getExpenseTypes() {
        return mapper.toExpenseTypeDtoList(repository.findAll());
    }

    @Override
    public ExpenseTypeDto createExpenseType(ExpenseTypeDto expenseTypeDto) {
        ExpenseType expenseType = mapper.toExpenseType(expenseTypeDto);
        return mapper.toExpenseTypeDto(repository.save(expenseType));
    }
}
