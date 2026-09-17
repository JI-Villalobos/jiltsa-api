package com.jiltsa.admin.orders.domain.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record OrderDto(
        Integer id, @NotNull Integer providerId, @NotNull Integer branchId, @NotNull LocalDate creationDate,
        @NotNull Double estimatedCost, @NotNull Double realCost,@NotNull Integer status, @NotNull Boolean isOpen, List<OrderItemDto> items
) {

    public OrderDto(
            Integer providerId, Integer branchId, LocalDate creationDate, Double estimatedCost, Double realCost,
            Integer status, Boolean isOpen) {
        this(null, providerId, branchId, creationDate, estimatedCost, realCost, status, isOpen, null);
    }

}
