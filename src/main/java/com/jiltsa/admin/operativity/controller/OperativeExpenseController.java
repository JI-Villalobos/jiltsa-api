package com.jiltsa.admin.operativity.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.operativity.domain.dto.OperativeExpenseDto;
import com.jiltsa.admin.operativity.domain.service.OperativeExpenseService;
import com.jiltsa.admin.security.AdminOnly;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/operative-expense")
@RequiredArgsConstructor
public class OperativeExpenseController {
    private final OperativeExpenseService service;

    @Operation(summary = "List a branch's operative expenses after a date")
    @GetMapping("/get-expenses")
    public List<OperativeExpenseDto> getOperativeExpenses(
            @Parameter(description = "Branch id") @RequestParam Integer branchId,
            @Parameter(description = "Expenses dated after this instant", example = "2026-03-01T00:00:00") @RequestParam LocalDateTime date
            ){
        return service.getOperativeExpensesByBranch(branchId, date);
    }

    @Operation(summary = "Save an operative expense")
    @PostMapping
    public OperativeExpenseDto saveOperativeExpense(@Valid @RequestBody OperativeExpenseDto operativeExpenseDto){
        return service.saveOperativeExpense(operativeExpenseDto);
    }

    @Operation(summary = "Delete an operative expense", description = "The expense to delete goes in the request body.")
    @AdminOnly
    @DeleteMapping
    public void deleteOperativeExpense(@RequestBody OperativeExpenseDto operativeExpenseDto){
        service.deleteOperativeExpense(operativeExpenseDto);
    }
}
