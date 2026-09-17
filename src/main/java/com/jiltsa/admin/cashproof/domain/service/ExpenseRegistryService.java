package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.CreateExpenseRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.ExpenseRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.ExpenseReportDto;
import com.jiltsa.admin.cashproof.persistence.entity.ExpenseRegistry;
import com.jiltsa.admin.cashproof.persistence.mapper.ExpenseRegistryMapper;
import com.jiltsa.admin.cashproof.persistence.repository.ExpenseRegistryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExpenseRegistryService {
    private final ExpenseRegistryRepository repository;
    private final ExpenseRegistryMapper mapper;

    public List<ExpenseRegistryDto> getExpenseRegistries(Integer accountingId) {
        return mapper.toExpenseRegistryDtoList(repository.findByAccountingId(accountingId));
    }

    @Transactional
    public CreateExpenseRegistryDto createExpenseRegistry(CreateExpenseRegistryDto createExpenseRegistryDto) {
        ExpenseRegistry expenseRegistry = mapper.toExpenseRegistry(createExpenseRegistryDto);
        return mapper.toCreateExpenseRegistryDto(repository.save(expenseRegistry));
    }

    @Transactional
    public ExpenseRegistryDto updateExpenseRegistry(ExpenseRegistryDto expenseRegistryDto) {
        ExpenseRegistry expenseRegistry = mapper.toExpenseRegistry(expenseRegistryDto);
        return mapper.toExpenseRegistryDto(repository.save(expenseRegistry));
    }

    @Transactional
    public void deleteExpenseRegistry(Integer expenseRegistryId) {
        repository.deleteById(expenseRegistryId);
    }

    public List<ExpenseReportDto> getExpenseReport(Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate) {
        return mapper.toExpenseReportDtoList(repository.getExpenseReport(branchId, initialDate, finalDate));
    }

    public List<ExpenseReportDto> getPharmacyExpenseReport(Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate) {
        return mapper.toExpenseReportDtoList(repository.getPharmacyExpenseReport(branchId, initialDate, finalDate));
    }
}
