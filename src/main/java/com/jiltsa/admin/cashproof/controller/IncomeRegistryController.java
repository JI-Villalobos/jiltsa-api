package com.jiltsa.admin.cashproof.controller;

import com.jiltsa.admin.cashproof.domain.dto.CreateIncomeRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.IncomeRegistryDto;
import com.jiltsa.admin.cashproof.domain.service.IncomeRegistryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/incomes")
@RequiredArgsConstructor
public class IncomeRegistryController {

    public final IncomeRegistryService service;
    @GetMapping("/{accountingId}")
    public List<IncomeRegistryDto> getIncomeRegistries(@PathVariable("accountingId") Integer accountingId){
        return service.getIncomeRegistries(accountingId);
    }

    @PostMapping
    public CreateIncomeRegistryDto createIncomeRegistry(@RequestBody CreateIncomeRegistryDto createIncomeRegistryDto){
        return service.createIncomeRegistry(createIncomeRegistryDto);
    }

}
