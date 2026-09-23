package com.jiltsa.admin.sales.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/** A product key is unique within its branch; other branches may reuse it. */
public record CreateProductDto(
        @NotBlank String key,
        String category,
        String description,
        @NotNull Integer branchId
) {
}
