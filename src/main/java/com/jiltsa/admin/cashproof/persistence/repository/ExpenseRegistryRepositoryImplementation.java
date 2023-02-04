package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.domain.dto.CreateExpenseRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.ExpenseRegistryDto;
import com.jiltsa.admin.cashproof.domain.repository.ExpenseRegistryDRepository;
import com.jiltsa.admin.cashproof.persistence.entity.ExpenseRegistry;
import com.jiltsa.admin.cashproof.persistence.mapper.ExpenseRegistryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ExpenseRegistryRepositoryImplementation implements ExpenseRegistryDRepository {
    private final ExpenseRegistryRepository repository;
    private final ExpenseRegistryMapper mapper;
    @Override
    public List<ExpenseRegistryDto> getExpenseRegistries(Integer accountingId) {
        return mapper.toExpenseRegistryDtoList(repository.findByAccountingId(accountingId));
    }

    @Override
    public CreateExpenseRegistryDto createExpenseRegistry(CreateExpenseRegistryDto createExpenseRegistryDto) {
        ExpenseRegistry expenseRegistry = mapper.toExpenseRegistry(createExpenseRegistryDto);
        return mapper.toCreateExpenseRegistryDto(repository.save(expenseRegistry));
    }
}
