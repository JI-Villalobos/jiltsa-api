package com.jiltsa.admin.operativity.projection;

import com.jiltsa.admin.cashproof.domain.dto.ExpenseReportDto;
import com.jiltsa.admin.cashproof.domain.service.ExpenseRegistryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LocalExpensesProjectionService {
    private final ExpenseRegistryService expenseRegistryService;

    public List<ExpenseReportDto> getExpensesReport(Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate){
        return expenseRegistryService.getExpenseReport(branchId, initialDate, finalDate);
    }
}
