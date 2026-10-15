package com.jiltsa.admin.orders.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.util.List;

public record OrderDto(
        Integer id, @NotNull Integer providerId, @NotNull Integer branchId, @NotNull LocalDate creationDate,
        @NotNull @PositiveOrZero Double estimatedCost, @NotNull @PositiveOrZero Double realCost,@NotNull Integer status, @NotNull Boolean isOpen, List<OrderItemDto> items
) {

    public OrderDto(
            Integer providerId, Integer branchId, LocalDate creationDate, Double estimatedCost, Double realCost,
            Integer status, Boolean isOpen) {
        this(null, providerId, branchId, creationDate, estimatedCost, realCost, status, isOpen, null);
    }

}
