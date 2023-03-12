package com.jiltsa.admin.cashproof.controller;

import com.jiltsa.admin.cashproof.domain.dto.ExpenseTypeDto;
import com.jiltsa.admin.cashproof.domain.service.ExpenseTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/expenses-types")
@RequiredArgsConstructor
public class ExpenseTypeController {
    private final ExpenseTypeService service;

    @GetMapping
    public List<ExpenseTypeDto> getExpenseTypes(){
        return service.getExpenseTypes();
    }

    @PostMapping
    public ExpenseTypeDto createExpenseType(ExpenseTypeDto expenseTypeDto){
        return service.createExpenseType(expenseTypeDto);
    }

}
