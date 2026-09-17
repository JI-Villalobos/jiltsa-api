package com.jiltsa.admin.orders.domain.service;

import com.jiltsa.admin.orders.domain.dto.OrderDto;
import com.jiltsa.admin.orders.domain.repository.OrderDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderDRepository orderDRepository;

    public List<OrderDto> getActiveOrders(){
        return orderDRepository.getActiveOrders();
    }

    public Optional<OrderDto> getOrder(Integer orderId){
        return orderDRepository.getOrder(orderId);
    }

    @Transactional
    public OrderDto saveOrder(OrderDto orderDto){
        return orderDRepository.saveOrder(orderDto);
    }

    @Transactional
    public void deleteOrder(Integer orderId){
        orderDRepository.deleteOrder(orderId);
    }

    public List<OrderDto> getActiveOrdersByBranch(Integer branchId){
        return orderDRepository.getActiveOrdersByBranch(branchId);
    }
}
