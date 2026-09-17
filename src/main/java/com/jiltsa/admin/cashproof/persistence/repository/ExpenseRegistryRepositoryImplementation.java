package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.domain.dto.CreateExpenseRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.ExpenseRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.ExpenseReportDto;
import com.jiltsa.admin.cashproof.domain.repository.ExpenseRegistryDRepository;
import com.jiltsa.admin.cashproof.persistence.entity.ExpenseRegistry;
import com.jiltsa.admin.cashproof.persistence.mapper.ExpenseRegistryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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

    @Override
    public ExpenseRegistryDto updateExpenseRegistry(ExpenseRegistryDto expenseRegistryDto) {
        ExpenseRegistry expenseRegistry = mapper.toExpenseRegistry(expenseRegistryDto);
        return mapper.toExpenseRegistryDto(repository.save(expenseRegistry));
    }

    @Override
    public void deleteExpenseRegistry(Integer expenseRegistryId) {
        repository.deleteById(expenseRegistryId);
    }

    @Override
    public List<ExpenseReportDto> getExpenseReport(Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate) {
        return mapper.toExpenseReportDtoList(repository.getExpenseReport(branchId, initialDate, finalDate));
    }

    @Override
    public List<ExpenseReportDto> getPharmacyExpenseReport(Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate) {
        return mapper.toExpenseReportDtoList(repository.getPharmacyExpenseReport(branchId, initialDate, finalDate));
    }
}
