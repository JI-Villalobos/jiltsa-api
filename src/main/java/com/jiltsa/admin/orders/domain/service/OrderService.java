package com.jiltsa.admin.orders.domain.service;

import com.jiltsa.admin.orders.domain.dto.OrderDto;
import com.jiltsa.admin.orders.persistence.entity.Order;
import com.jiltsa.admin.orders.persistence.mapper.OrderMapper;
import com.jiltsa.admin.orders.persistence.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final OrderMapper mapper;

    public List<OrderDto> getActiveOrders() {
        return mapper.toOrderDtoList(repository.findByIsOpenTrue());
    }

    public Optional<OrderDto> getOrder(Integer orderId) {
        return repository.findById(orderId).map(mapper::toOrderDto);
    }

    @Transactional
    public OrderDto saveOrder(OrderDto orderDto) {
        Order order = mapper.toOrder(orderDto);
        return mapper.toOrderDto(repository.save(order));
    }

    @Transactional
    public void deleteOrder(Integer orderId) {
        repository.deleteById(orderId);
    }

    public List<OrderDto> getActiveOrdersByBranch(Integer branchId) {
        return mapper.toOrderDtoList(repository.findByBranchIdAndIsOpenTrue(branchId));
    }
}
