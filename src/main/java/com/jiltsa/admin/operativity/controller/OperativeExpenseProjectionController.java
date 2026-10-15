package com.jiltsa.admin.operativity.controller;

import com.jiltsa.admin.operativity.domain.dto.OperativeCostDto;
import com.jiltsa.admin.operativity.domain.dto.OperativeExpenseProjectionDto;
import com.jiltsa.admin.operativity.domain.dto.OperativeExpenseTotalsDto;
import com.jiltsa.admin.operativity.projection.OperativeExpensesProjectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("jiltsa/api/v1/operative-expense-projection")
@RequiredArgsConstructor
public class OperativeExpenseProjectionController {
    private final OperativeExpensesProjectionService operativeExpensesProjectionService;

    @Operation(summary = "Total a branch's operative expenses by category within a date range")
    @GetMapping
    public OperativeExpenseProjectionDto getOperativeExpenseProjection(
            @Parameter(description = "Branch id") @RequestParam Integer branchId,
            @Parameter(description = "Range start", example = "2026-03-01T00:00:00") @RequestParam LocalDateTime initialDate,
            @Parameter(description = "Range end", example = "2026-03-31T23:59:59") @RequestParam LocalDateTime finalDate
    ){
        return operativeExpensesProjectionService.getExpensesProjection(branchId, initialDate, finalDate);
    }

    @Operation(summary = "Get a branch's pharmacy operating cost within a date range", description = "Pharmacy operative expenses plus pharmacy-type cash-proof expenses.")
    @GetMapping("/cost")
    public OperativeCostDto getOperativeCost(
            @Parameter(description = "Branch id") @RequestParam Integer branchId,
            @Parameter(description = "Range start", example = "2026-03-01T00:00:00") @RequestParam LocalDateTime initialDate,
            @Parameter(description = "Range end", example = "2026-03-31T23:59:59") @RequestParam LocalDateTime finalDate
    ){
        return operativeExpensesProjectionService.getOperativeCost(branchId, initialDate, finalDate);
    }

    @Operation(summary = "Total a branch's operative expenses within a date range")
    @GetMapping("/expense-totals")
    public OperativeExpenseTotalsDto getExpenseTotals(
            @Parameter(description = "Branch id") @RequestParam Integer branchId,
            @Parameter(description = "Range start", example = "2026-03-01T00:00:00") @RequestParam LocalDateTime initialDate,
            @Parameter(description = "Range end", example = "2026-03-31T23:59:59") @RequestParam LocalDateTime finalDate
    ){
        return operativeExpensesProjectionService.getTotalExpenses(branchId, initialDate, finalDate);
    }
}
