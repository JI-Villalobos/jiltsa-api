package com.jiltsa.admin.sales.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** The id is assigned on creation; it is ignored when creating or updating. */
public record CategoryDto(
        Integer id,
        @NotBlank @Size(max = 255) String categoryName
) {
}
