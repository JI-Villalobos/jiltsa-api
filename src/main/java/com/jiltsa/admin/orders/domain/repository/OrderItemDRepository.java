package com.jiltsa.admin.orders.domain.repository;

import com.jiltsa.admin.orders.domain.dto.OrderItemDto;

import java.util.List;
import java.util.Optional;

public interface OrderItemDRepository {
    List<OrderItemDto> saveOrders(List<OrderItemDto> items);
    OrderItemDto saveOrderItem(OrderItemDto item);
    Optional<OrderItemDto> getOrderItem(Integer itemId);
    void deleteOrderItem(Integer itemId);
    void disableItems(Integer orderId, Integer status);
}
