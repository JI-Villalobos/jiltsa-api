package com.jiltsa.admin.sales.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

/** One ticket line as exported by the point of sale; totals and utility come precomputed from it. */
public record CreateSaleDto(
        @NotNull Integer branchId,
        @NotBlank String key,
        String description,
        @NotNull Long ticket,
        @NotBlank String category,
        @NotNull @Positive Integer quantity,
        @NotNull @PositiveOrZero Double price,
        @NotNull @PositiveOrZero Double purchasePrice,
        @NotNull Double approximatedUtility,
        @NotNull @PositiveOrZero Double total,
        @NotNull LocalDateTime timestamp,
        @NotBlank String user
) {
}
