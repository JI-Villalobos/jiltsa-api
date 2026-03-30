package com.jiltsa.admin.operativity.controller;

import com.jiltsa.admin.cashproof.persistence.repository.ExpenseResult;
import com.jiltsa.admin.operativity.projection.LocalExpensesProjectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/local-expense-results")
@RequiredArgsConstructor
public class LocalExpenseReportController {
    private final LocalExpensesProjectionService localExpensesProjectionService;

    @GetMapping
    public List<ExpenseResult> getReport(
            @RequestParam Integer branchId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime initialDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime finalDate
    ){
        return localExpensesProjectionService.getExpensesReport(branchId, initialDate, finalDate);
    }
}
