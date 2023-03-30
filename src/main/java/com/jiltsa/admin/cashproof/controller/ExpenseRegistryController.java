package com.jiltsa.admin.cashproof.controller;

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
    public CreateExpenseRegistryDto createExpenseRegistry(@RequestBody CreateExpenseRegistryDto createExpenseRegistryDto){
        return service.createExpenseRegistry(createExpenseRegistryDto);
    }
}
