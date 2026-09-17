package com.jiltsa.admin.operativity.domain.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record OperativeExpenseDto(
        Integer id, @NotNull Integer branchId, String invoice, @NotNull LocalDateTime expenseDate, Integer providerId, @NotNull Double amount, @NotNull String concept, @NotNull String category
) {
}
