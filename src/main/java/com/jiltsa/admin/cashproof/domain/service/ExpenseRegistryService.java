package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.CreateExpenseRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.ExpenseRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.ExpenseReportDto;
import com.jiltsa.admin.cashproof.domain.repository.ExpenseRegistryDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExpenseRegistryService {
    private final ExpenseRegistryDRepository expenseRegistryDRepository;

    public List<ExpenseRegistryDto> getExpenseRegistries(Integer accountingId){
        return expenseRegistryDRepository.getExpenseRegistries(accountingId);
    }

    @Transactional
    public CreateExpenseRegistryDto createExpenseRegistry(CreateExpenseRegistryDto createExpenseRegistryDto){
        return expenseRegistryDRepository.createExpenseRegistry(createExpenseRegistryDto);
    }

    @Transactional
    public ExpenseRegistryDto updateExpenseRegistry(ExpenseRegistryDto expenseRegistryDto){
        return expenseRegistryDRepository.updateExpenseRegistry(expenseRegistryDto);
    }

    @Transactional
    public void deleteExpenseRegistry(Integer expenseRegistryId){
        expenseRegistryDRepository.deleteExpenseRegistry(expenseRegistryId);
    }

    public List<ExpenseReportDto> getExpenseReport(Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate){
        return expenseRegistryDRepository.getExpenseReport(branchId, initialDate, finalDate);
    }

    public List<ExpenseReportDto> getPharmacyExpenseReport(Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate){
        return expenseRegistryDRepository.getPharmacyExpenseReport(branchId, initialDate, finalDate);
    }
}
