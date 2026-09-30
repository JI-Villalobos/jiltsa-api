package com.jiltsa.admin.cashproof.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.cashproof.domain.dto.CreateIncomeRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.IncomeRegistryDto;
import com.jiltsa.admin.cashproof.domain.service.IncomeRegistryService;
import com.jiltsa.admin.security.AdminOnly;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/incomes")
@RequiredArgsConstructor
public class IncomeRegistryController {

    public final IncomeRegistryService service;
    @Operation(summary = "List the incomes of an accounting")
    @GetMapping("/{accountingId}")
    public List<IncomeRegistryDto> getIncomeRegistries(@Parameter(description = "Accounting id") @PathVariable Integer accountingId){
        return service.getIncomeRegistries(accountingId);
    }

    @Operation(summary = "Register an income in an accounting")
    @PostMapping
    public CreateIncomeRegistryDto createIncomeRegistry(@Valid @RequestBody CreateIncomeRegistryDto createIncomeRegistryDto){
        return service.createIncomeRegistry(createIncomeRegistryDto);
    }

    @Operation(summary = "Update an income")
    @PutMapping()
    public IncomeRegistryDto updateIncomeRegistry(
            @Valid @RequestBody IncomeRegistryDto incomeRegistryDto){
        return service.updateIncomeRegistry(incomeRegistryDto);
    }

    @Operation(summary = "Register several incomes")
    @PostMapping("/all")
    public List<IncomeRegistryDto> createIncomes(@RequestBody List<@Valid CreateIncomeRegistryDto> incomes){
        return service.createIncomesRegistry(incomes);
    }

    @Operation(summary = "Delete an income")
    @AdminOnly
    @DeleteMapping("/{incomeRegistryId}")
    public void deleteIncomeRegistry(@Parameter(description = "Income id") @PathVariable Integer incomeRegistryId){
        service.deleteIncomeRegistry(incomeRegistryId);
    }
}
