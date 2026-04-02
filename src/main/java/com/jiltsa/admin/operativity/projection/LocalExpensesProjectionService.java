package com.jiltsa.admin.operativity.projection;

import com.jiltsa.admin.cashproof.persistence.repository.ExpenseRegistryRepository;
import com.jiltsa.admin.cashproof.persistence.repository.ExpenseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalExpensesProjectionService {
    private final ExpenseRegistryRepository expenseRegistryRepository;

    public List<ExpenseResult> getExpensesReport(Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate){
        return expenseRegistryRepository.getExpenseReport(branchId, initialDate, finalDate);
    }
}
