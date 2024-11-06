package com.jiltsa.admin.orders.domain.repository;

import com.jiltsa.admin.orders.domain.dto.OrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderDRepository {
    List<OrderDto> getActiveOrders();
    Optional<OrderDto> getOrder(Integer orderId);
    OrderDto saveOrder(OrderDto order);
    void deleteOrder(Integer orderId);
}
