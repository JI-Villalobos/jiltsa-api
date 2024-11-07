package com.jiltsa.admin.orders.domain.service;

import com.jiltsa.admin.orders.domain.dto.OrderItemDto;
import com.jiltsa.admin.orders.domain.repository.OrderItemDRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemDRepository orderItemDRepository;

    public OrderItemDto saveOrderItem(OrderItemDto orderItemDto){
        return orderItemDRepository.saveOrderItem(orderItemDto);
    }

    @Transactional
    public List<OrderItemDto> saveOrderItemDtoList(List<OrderItemDto> orderItemDtoList){
        return orderItemDRepository.saveOrders(orderItemDtoList);
    }

    public Optional<OrderItemDto> getOrderItem(Integer orderItemId){
        return orderItemDRepository.getOrderItem(orderItemId);
    }

    public void deleteOrderItem(Integer orderItemId){
        orderItemDRepository.deleteOrderItem(orderItemId);
    }

    public void disableItems(Integer orderId){
        orderItemDRepository.disableItems(orderId);
    }
}
