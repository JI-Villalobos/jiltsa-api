package com.jiltsa.admin.promotions.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/** The id is assigned on creation; {@code quantity} is what is still on the shelf. */
public record PromotionItemDto(
        Long id,
        @NotBlank @Size(max = 255) String key,
        @Size(max = 255) String description,
        @NotNull @PositiveOrZero Integer quantity,
        @NotNull @PositiveOrZero Double normalPrice,
        @NotNull @PositiveOrZero Double promotionPrice,
        @NotNull @PositiveOrZero Double purchasePrice,
        @NotNull Integer promotionId,
        @NotNull LocalDate expirationDate
) {
}
