package com.jiltsa.admin.orders.domain.dto;

import jakarta.validation.constraints.NotNull;
public record OrderItemDto(
        Long id, @NotNull Integer orderId, @NotNull String item, @NotNull Integer requested, @NotNull Double price, @NotNull Double budgeted,
        @NotNull String itemType, @NotNull Integer stocked, @NotNull Double finalPrice, @NotNull Double total, @NotNull Integer status) {

    public OrderItemDto(
            Integer orderId, String item, Integer requested, Double price, Double budgeted, String itemType,
            Integer stocked, Double finalPrice, Double total, Integer status) {
        this(null, orderId, item, requested, price, budgeted, itemType, stocked, finalPrice, total, status);
    }
}
