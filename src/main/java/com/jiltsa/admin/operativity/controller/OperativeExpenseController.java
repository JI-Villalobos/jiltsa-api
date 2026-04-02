package com.jiltsa.admin.operativity.controller;

import com.jiltsa.admin.operativity.domain.dto.OperativeExpenseDto;
import com.jiltsa.admin.operativity.domain.service.OperativeExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/operative-expense")
@RequiredArgsConstructor
public class OperativeExpenseController {
    private final OperativeExpenseService service;

    @GetMapping("/get-expenses")
    public List<OperativeExpenseDto> getOperativeExpenses(
            @RequestParam Integer branchId,
            @RequestParam LocalDateTime date
            ){
        return service.getOperativeExpensesByBranch(branchId, date);
    }

    @PostMapping
    public OperativeExpenseDto saveOperativeExpense(@RequestBody OperativeExpenseDto operativeExpenseDto){
        return service.saveOperativeExpense(operativeExpenseDto);
    }

    @DeleteMapping
    public void deleteOperativeExpense(@RequestBody OperativeExpenseDto operativeExpenseDto){
        service.deleteOperativeExpense(operativeExpenseDto);
    }
}
