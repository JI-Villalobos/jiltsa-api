package com.jiltsa.admin.cashproof.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.cashproof.domain.dto.CreateIncomeRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.IncomeRegistryDto;
import com.jiltsa.admin.cashproof.domain.service.IncomeRegistryService;
import com.jiltsa.admin.security.AdminOnly;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/incomes")
@RequiredArgsConstructor
public class IncomeRegistryController {

    public final IncomeRegistryService service;
    @GetMapping("/{accountingId}")
    public List<IncomeRegistryDto> getIncomeRegistries(@PathVariable Integer accountingId){
        return service.getIncomeRegistries(accountingId);
    }

    @PostMapping
    public CreateIncomeRegistryDto createIncomeRegistry(@Valid @RequestBody CreateIncomeRegistryDto createIncomeRegistryDto){
        return service.createIncomeRegistry(createIncomeRegistryDto);
    }

    @PutMapping()
    public IncomeRegistryDto updateIncomeRegistry(
            @Valid @RequestBody IncomeRegistryDto incomeRegistryDto){
        return service.updateIncomeRegistry(incomeRegistryDto);
    }

    @PostMapping("/all")
    public List<IncomeRegistryDto> createIncomes(@RequestBody List<@Valid CreateIncomeRegistryDto> incomes){
        return service.createIncomesRegistry(incomes);
    }

    @AdminOnly
    @DeleteMapping("/{incomeRegistryId}")
    public void deleteIncomeRegistry(@PathVariable Integer incomeRegistryId){
        service.deleteIncomeRegistry(incomeRegistryId);
    }
}
