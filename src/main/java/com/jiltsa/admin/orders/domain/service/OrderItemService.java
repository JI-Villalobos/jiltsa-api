package com.jiltsa.admin.orders.domain.service;

import com.jiltsa.admin.orders.domain.dto.OrderItemDto;
import com.jiltsa.admin.orders.domain.repository.OrderItemDRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {
    private final OrderItemDRepository orderItemDRepository;

    public OrderItemService(OrderItemDRepository orderItemDRepository) {
        this.orderItemDRepository = orderItemDRepository;
    }

    public OrderItemDto saveOrderItem(OrderItemDto orderItemDto){
        return orderItemDRepository.saveOrderItem(orderItemDto);
    }

    @Transactional
    public List<OrderItemDto> saveOrderItemDtoList(List<OrderItemDto> orderItemDtoList){
        return orderItemDRepository.saveOrders(orderItemDtoList);
    }

    public Optional<OrderItemDto> getOrderItem(Integer orderItemId){
        return orderItemDRepository.getorderItem(orderItemId);
    }

    public Boolean deleteOrderItem(Integer orderItemId){
        return orderItemDRepository.deleteOrderItem(orderItemId);
    }
}
