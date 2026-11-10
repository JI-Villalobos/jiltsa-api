package com.jiltsa.admin.promotions.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

/**
 * The id is assigned on creation. {@code creationDate} defaults to today and {@code active} to
 * true when omitted. Items are read-only here: they are managed through /promotion-items.
 */
public record PromotionDto(
        Integer id,
        @NotBlank @Size(max = 255) String promotionName,
        @NotNull Integer branchId,
        LocalDate creationDate,
        @NotNull LocalDate promotionEndDate,
        Boolean active,
        List<PromotionItemDto> items
) {
}
