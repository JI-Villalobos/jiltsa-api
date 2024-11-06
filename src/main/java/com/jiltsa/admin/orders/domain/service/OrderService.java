package com.jiltsa.admin.orders.domain.service;

import com.jiltsa.admin.orders.domain.dto.OrderDto;
import com.jiltsa.admin.orders.domain.repository.OrderDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderDRepository orderDRepository;

    public List<OrderDto> getActiveOrders(){
        return orderDRepository.getActiveOrders();
    }

    public Optional<OrderDto> getOrder(Integer orderId){
        return orderDRepository.getOrder(orderId);
    }

    public OrderDto saveOrder(OrderDto orderDto){
        return orderDRepository.saveOrder(orderDto);
    }

    public void deleteOrder(Integer orderId){
        orderDRepository.deleteOrder(orderId);
    }
}
