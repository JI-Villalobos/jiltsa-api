package com.jiltsa.admin.operativity.controller;

import com.jiltsa.admin.operativity.domain.dto.OperativeExpenseProjectionDto;
import com.jiltsa.admin.operativity.projection.OperativeExpensesProjectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("jiltsa/api/v1/operative-expense-projection")
@RequiredArgsConstructor
public class OperativeExpenseProjectionController {
    private final OperativeExpensesProjectionService operativeExpensesProjectionService;

    @GetMapping
    public OperativeExpenseProjectionDto getOperativeExpenseProjection(
            @RequestParam Integer branchId,
            @RequestParam LocalDateTime initialDate,
            @RequestParam LocalDateTime finalDate
    ){
        return operativeExpensesProjectionService.getExpensesProjection(branchId, initialDate, finalDate);
    }
}
