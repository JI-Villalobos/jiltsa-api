package com.jiltsa.admin.cashproof.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.cashproof.domain.dto.CreateExpenseRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.ExpenseRegistryDto;
import com.jiltsa.admin.cashproof.domain.service.ExpenseRegistryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/expenses")
@RequiredArgsConstructor
public class ExpenseRegistryController {
    private final ExpenseRegistryService service;

    @GetMapping("/{accountingId}")
    public List<ExpenseRegistryDto> getExpenseRegistries(@PathVariable("accountingId") Integer accountingId){
        return service.getExpenseRegistries(accountingId);
    }

    @PostMapping
    public CreateExpenseRegistryDto createExpenseRegistry(@Valid @RequestBody CreateExpenseRegistryDto createExpenseRegistryDto){
        return service.createExpenseRegistry(createExpenseRegistryDto);
    }

    @PutMapping()
    public ExpenseRegistryDto updateExpenseRegistry(
            @Valid @RequestBody ExpenseRegistryDto expenseRegistryDto){
        return service.updateExpenseRegistry(expenseRegistryDto);
    }

    @DeleteMapping("/{expenseRegistryId}")
    public void deleteExpenseRegistry(@PathVariable Integer expenseRegistryId){
        service.deleteExpenseRegistry(expenseRegistryId);
    }
}
