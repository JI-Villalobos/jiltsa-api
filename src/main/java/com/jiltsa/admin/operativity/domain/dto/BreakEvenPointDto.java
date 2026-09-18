package com.jiltsa.admin.operativity.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
public record BreakEvenPointDto(
            Integer id, @NotNull Integer branchId, @NotNull @PositiveOrZero Double paysheet, @NotNull @PositiveOrZero Double rent,
            @NotNull @PositiveOrZero Double electricityService, @NotNull @PositiveOrZero Double internet, @NotNull @PositiveOrZero Double otherServices,
            @NotNull @PositiveOrZero Double gasolineAndTransport, @NotNull @PositiveOrZero Double adminPayment, @NotNull @PositiveOrZero Double operatingCost,
            @NotNull @PositiveOrZero Double otherExpenses, @NotNull @PositiveOrZero Double fixedExpenses, @NotNull @PositiveOrZero Double pe
            ) {
}
