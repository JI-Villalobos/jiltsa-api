package com.jiltsa.admin.cashproof.controller;

import com.jiltsa.admin.cashproof.domain.dto.IncomeTypeDto;
import com.jiltsa.admin.cashproof.domain.service.IncomeTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/income-types")
@RequiredArgsConstructor
public class IncomeTypeController {
    private final IncomeTypeService service;
    @GetMapping
    public List<IncomeTypeDto> getIncomeTypes(){
        return service.getIncomeTypes();
    }

    @PostMapping
    public IncomeTypeDto createIncomeType(@RequestBody IncomeTypeDto incomeTypeDto){
        return service.createIncomeType(incomeTypeDto);
    }

}
