package com.jiltsa.admin.orders.persistence.repository;

import com.jiltsa.admin.orders.domain.dto.OrderDto;
import com.jiltsa.admin.orders.domain.repository.OrderDRepository;
import com.jiltsa.admin.orders.persistence.entity.Order;
import com.jiltsa.admin.orders.persistence.mapper.OrderMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImplementation implements OrderDRepository {
    private final OrderRepository repository;
    private final OrderMapper mapper;

    public OrderRepositoryImplementation(OrderRepository repository, OrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<OrderDto> getActiveOrders() {
        return mapper.toOrderDtoList(repository.findByIsOpenTrue());
    }

    @Override
    public Optional<OrderDto> getOrder(Integer orderId) {
        return repository.findById(orderId).map(mapper::toOrderDto);
    }

    @Override
    public OrderDto saveOrder(OrderDto orderDto) {
        Order order = mapper.toOrder(orderDto);
        return mapper.toOrderDto(repository.save(order));
    }

    @Override
    public void deleteOrder(Integer orderId) {
        repository.deleteById(orderId);
    }
}
