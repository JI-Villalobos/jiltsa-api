package com.jiltsa.admin.operativity.domain.dto;

import jakarta.validation.constraints.NotNull;
public record BreakEvenPointDto(
            Integer id, @NotNull Integer branchId, @NotNull Double paysheet, @NotNull Double rent,
            @NotNull Double electricityService, @NotNull Double internet, @NotNull Double otherServices,
            @NotNull Double gasolineAndTransport, @NotNull Double adminPayment, @NotNull Double operatingCost,
            @NotNull Double otherExpenses, @NotNull Double fixedExpenses, @NotNull Double pe
            ) {
}
