package com.jiltsa.admin.sales.domain.dto;

public record ProductDto(
        Long id,
        String key,
        String category,
        String description,
        Integer branchId
) {
}
