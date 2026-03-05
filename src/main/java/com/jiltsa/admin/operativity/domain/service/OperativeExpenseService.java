package com.jiltsa.admin.operativity.domain.service;

import com.jiltsa.admin.operativity.domain.dto.OperativeExpenseDto;
import com.jiltsa.admin.operativity.domain.repository.OperativeExpenseDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OperativeExpenseService {
    private final OperativeExpenseDRepository repository;

    public List<OperativeExpenseDto> getOperativeExpensesByBranch(Integer branchId, LocalDateTime date){
        return repository.getOperativeExpensesByBranch(branchId, date);
    }

    public OperativeExpenseDto saveOperativeExpense(OperativeExpenseDto operativeExpenseDto){
        return repository.saveOperativeExpense(operativeExpenseDto);
    }

    public void deleteOperativeExpense(OperativeExpenseDto operativeExpenseDto){
        repository.deleteOperativeExpense(operativeExpenseDto);
    }
}
