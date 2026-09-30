package com.jiltsa.admin.cashproof.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.cashproof.domain.dto.ExpenseTypeDto;
import com.jiltsa.admin.cashproof.domain.service.ExpenseTypeService;
import com.jiltsa.admin.security.AdminOnly;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/expenses-types")
@RequiredArgsConstructor
public class ExpenseTypeController {
    private final ExpenseTypeService service;

    @Operation(summary = "List expense types")
    @GetMapping
    public List<ExpenseTypeDto> getExpenseTypes(){
        return service.getExpenseTypes();
    }

    @Operation(summary = "Create an expense type")
    @AdminOnly
    @PostMapping
    public ExpenseTypeDto createExpenseType(@Valid @RequestBody ExpenseTypeDto expenseTypeDto){
        return service.createExpenseType(expenseTypeDto);
    }

}
