package com.jiltsa.admin.operativity.domain.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record SaleResultDto(
        Integer id, @NotNull Double pharmacyAmount, @NotNull Double servicesAmount,
        @NotNull Integer weekNumber, @NotNull Integer year, @NotNull LocalDateTime initialDate, @NotNull LocalDateTime finalDate, @NotNull Integer branchId
) {
}
