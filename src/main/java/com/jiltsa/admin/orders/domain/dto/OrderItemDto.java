package com.jiltsa.admin.orders.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
public record OrderItemDto(
        Long id, @NotNull Integer orderId, @NotBlank String item, @NotNull @PositiveOrZero Integer requested, @NotNull @PositiveOrZero Double price, @NotNull @PositiveOrZero Double budgeted,
        @NotBlank String itemType, @NotNull @PositiveOrZero Integer stocked, @NotNull @PositiveOrZero Double finalPrice, @NotNull @PositiveOrZero Double total, @NotNull Integer status) {

    public OrderItemDto(
            Integer orderId, String item, Integer requested, Double price, Double budgeted, String itemType,
            Integer stocked, Double finalPrice, Double total, Integer status) {
        this(null, orderId, item, requested, price, budgeted, itemType, stocked, finalPrice, total, status);
    }
}
