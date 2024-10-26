package com.jiltsa.admin.orders.domain.dto;

import java.sql.Date;
import java.util.List;

public record OrderDto(
        Integer id, Integer providerId, Integer branchId, Date creationDate,
        Double estimatedCost, Double realCost,Integer status, Boolean isOpen, List<OrderItemDto> items
) {

    public OrderDto(
            Integer providerId, Integer branchId, Date creationDate, Double estimatedCost, Double realCost,
            Integer status, Boolean isOpen) {
        this(0, providerId, branchId, creationDate, estimatedCost, realCost, status, isOpen, null);
    }
}
