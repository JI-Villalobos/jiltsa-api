package com.jiltsa.admin.operativity.controller;

import com.jiltsa.admin.cashproof.domain.dto.ExpenseReportDto;
import com.jiltsa.admin.operativity.projection.LocalExpensesProjectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Report a branch's cash-proof expenses by type within a date range")
    @GetMapping
    public List<ExpenseReportDto> getReport(
            @Parameter(description = "Branch id") @RequestParam Integer branchId,
            @Parameter(description = "Range start", example = "2026-03-01T00:00:00") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime initialDate,
            @Parameter(description = "Range end", example = "2026-03-31T23:59:59") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime finalDate
    ){
        return localExpensesProjectionService.getExpensesReport(branchId, initialDate, finalDate);
    }
}
