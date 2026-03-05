package com.jiltsa.admin.operativity.persistence.repository;

import com.jiltsa.admin.operativity.domain.dto.OperativeExpenseDto;
import com.jiltsa.admin.operativity.domain.repository.OperativeExpenseDRepository;
import com.jiltsa.admin.operativity.persistence.entity.OperativeExpense;
import com.jiltsa.admin.operativity.persistence.mapper.OperativeExpenseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OperativeExpenseRepositoryImplementation implements OperativeExpenseDRepository {
    private final OperativeExpenseRepository repository;
    private final OperativeExpenseMapper mapper;

    @Override
    public List<OperativeExpenseDto> getOperativeExpensesByBranch(Integer branchId, LocalDateTime date) {
        return mapper.toOperativeExpenseDtoList(repository.findByBranchIdAndExpenseDateAfter(branchId, date));
    }

    @Override
    public OperativeExpenseDto saveOperativeExpense(OperativeExpenseDto operativeExpenseDto) {
        OperativeExpense operativeExpense = mapper.toOperativeExpense(operativeExpenseDto);

        return mapper.toOperativeExpenseDto(repository.save(operativeExpense));
    }

    @Override
    public void deleteOperativeExpense(OperativeExpenseDto operativeExpenseDto) {
        OperativeExpense operativeExpense = mapper.toOperativeExpense(operativeExpenseDto);

        repository.delete(operativeExpense);
    }
}
