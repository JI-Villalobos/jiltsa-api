package com.jiltsa.admin.operativity.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record OperativeExpenseDto(
        Integer id, @NotNull Integer branchId, String invoice, @NotNull LocalDateTime expenseDate, Integer providerId, @NotNull @Positive Double amount, @NotBlank String concept, @NotBlank String category
) {
}
