package com.jiltsa.admin.cashproof.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.cashproof.domain.dto.CreateExpenseRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.ExpenseRegistryDto;
import com.jiltsa.admin.cashproof.domain.service.ExpenseRegistryService;
import com.jiltsa.admin.security.AdminOnly;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/expenses")
@RequiredArgsConstructor
public class ExpenseRegistryController {
    private final ExpenseRegistryService service;

    @Operation(summary = "List the expenses of an accounting")
    @GetMapping("/{accountingId}")
    public List<ExpenseRegistryDto> getExpenseRegistries(@Parameter(description = "Accounting id") @PathVariable("accountingId") Integer accountingId){
        return service.getExpenseRegistries(accountingId);
    }

    @Operation(summary = "Register an expense in an accounting")
    @PostMapping
    public CreateExpenseRegistryDto createExpenseRegistry(@Valid @RequestBody CreateExpenseRegistryDto createExpenseRegistryDto){
        return service.createExpenseRegistry(createExpenseRegistryDto);
    }

    @Operation(summary = "Update an expense")
    @PutMapping()
    public ExpenseRegistryDto updateExpenseRegistry(
            @Valid @RequestBody ExpenseRegistryDto expenseRegistryDto){
        return service.updateExpenseRegistry(expenseRegistryDto);
    }

    @Operation(summary = "Delete an expense")
    @AdminOnly
    @DeleteMapping("/{expenseRegistryId}")
    public void deleteExpenseRegistry(@Parameter(description = "Expense id") @PathVariable Integer expenseRegistryId){
        service.deleteExpenseRegistry(expenseRegistryId);
    }
}
