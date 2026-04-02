package com.jiltsa.admin.operativity.domain.dto;

import java.time.LocalDateTime;

public record OperativeExpenseDto(
        Integer id, Integer branchId, String invoice, LocalDateTime expenseDate, Integer providerId, Double amount, String concept, String category
) {
}
