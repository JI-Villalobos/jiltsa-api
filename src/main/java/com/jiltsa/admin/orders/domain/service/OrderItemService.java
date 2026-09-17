package com.jiltsa.admin.orders.domain.service;

import com.jiltsa.admin.orders.domain.dto.OrderItemDto;
import com.jiltsa.admin.orders.domain.repository.OrderItemDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemDRepository orderItemDRepository;

    @Transactional
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

    @Transactional
    public void deleteOrderItem(Integer orderItemId){
        orderItemDRepository.deleteOrderItem(orderItemId);
    }

    @Transactional
    public void disableItems(Integer orderId, Integer status){
        orderItemDRepository.disableItems(orderId, status);
    }
}
