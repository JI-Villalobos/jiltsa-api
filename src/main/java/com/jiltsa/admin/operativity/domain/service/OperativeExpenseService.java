package com.jiltsa.admin.operativity.domain.service;

import com.jiltsa.admin.operativity.domain.dto.OperativeExpenseDto;
import com.jiltsa.admin.operativity.domain.repository.OperativeExpenseDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OperativeExpenseService {
    private final OperativeExpenseDRepository repository;

    public List<OperativeExpenseDto> getOperativeExpensesByBranch(Integer branchId, LocalDateTime date){
        return repository.getOperativeExpensesByBranch(branchId, date);
    }

    @Transactional
    public OperativeExpenseDto saveOperativeExpense(OperativeExpenseDto operativeExpenseDto){
        return repository.saveOperativeExpense(operativeExpenseDto);
    }

    @Transactional
    public void deleteOperativeExpense(OperativeExpenseDto operativeExpenseDto){
        repository.deleteOperativeExpense(operativeExpenseDto);
    }
}
