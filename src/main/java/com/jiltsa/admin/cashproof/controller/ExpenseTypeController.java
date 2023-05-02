package com.jiltsa.admin.cashproof.controller;

import com.jiltsa.admin.cashproof.domain.dto.ExpenseTypeDto;
import com.jiltsa.admin.cashproof.domain.service.ExpenseTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/expenses-types")
@RequiredArgsConstructor
@CrossOrigin
public class ExpenseTypeController {
    private final ExpenseTypeService service;

    @GetMapping
    public List<ExpenseTypeDto> getExpenseTypes(){
        return service.getExpenseTypes();
    }

    @PostMapping
    public ExpenseTypeDto createExpenseType(@RequestBody ExpenseTypeDto expenseTypeDto){
        return service.createExpenseType(expenseTypeDto);
    }

}
