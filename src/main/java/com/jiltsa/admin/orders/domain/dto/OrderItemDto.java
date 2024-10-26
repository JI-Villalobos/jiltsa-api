package com.jiltsa.admin.orders.domain.dto;

public record OrderItemDto(
        Integer id, Integer orderId, String item, Integer requested, Double price, Double budgeted,
        String itemType, Integer stocked, Double finalPrice, Double total, Integer status) {

    public OrderItemDto(
            Integer orderId, String item, Integer requested, Double price, Double budgeted, String itemType,
            Integer stocked, Double finalPrice, Double total, Integer status) {
        this(0, orderId, item, requested, price, budgeted, itemType, stocked, finalPrice, total, status);
    }
}
