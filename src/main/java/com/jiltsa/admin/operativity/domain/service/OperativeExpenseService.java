package com.jiltsa.admin.operativity.domain.service;

import com.jiltsa.admin.operativity.domain.dto.OperativeExpenseDto;
import com.jiltsa.admin.operativity.persistence.entity.OperativeExpense;
import com.jiltsa.admin.operativity.persistence.mapper.OperativeExpenseMapper;
import com.jiltsa.admin.operativity.persistence.repository.OperativeExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OperativeExpenseService {
    private final OperativeExpenseRepository repository;
    private final OperativeExpenseMapper mapper;

    public List<OperativeExpenseDto> getOperativeExpensesByBranch(Integer branchId, LocalDateTime date) {
        return mapper.toOperativeExpenseDtoList(repository.findByBranchIdAndExpenseDateAfter(branchId, date));
    }

    @Transactional
    public OperativeExpenseDto saveOperativeExpense(OperativeExpenseDto operativeExpenseDto) {
        OperativeExpense operativeExpense = mapper.toOperativeExpense(operativeExpenseDto);

        return mapper.toOperativeExpenseDto(repository.save(operativeExpense));
    }

    @Transactional
    public void deleteOperativeExpense(OperativeExpenseDto operativeExpenseDto) {
        OperativeExpense operativeExpense = mapper.toOperativeExpense(operativeExpenseDto);

        repository.delete(operativeExpense);
    }
}
