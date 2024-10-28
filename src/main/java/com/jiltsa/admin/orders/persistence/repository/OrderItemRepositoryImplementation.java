package com.jiltsa.admin.orders.persistence.repository;

import com.jiltsa.admin.orders.domain.dto.OrderItemDto;
import com.jiltsa.admin.orders.domain.repository.OrderItemDRepository;
import com.jiltsa.admin.orders.persistence.entity.OrderItem;
import com.jiltsa.admin.orders.persistence.mapper.OrderItemMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderItemRepositoryImplementation implements OrderItemDRepository {
    private final OrderItemRepository repository;
    private final OrderItemMapper mapper;

    public OrderItemRepositoryImplementation(OrderItemRepository repository, OrderItemMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<OrderItemDto> saveOrders(List<OrderItemDto> items) {
        List<OrderItem> itemList = mapper.toOrderItemList(items);
        return mapper.toOrderItemDtoList(repository.saveAll(itemList));
    }

    @Override
    public OrderItemDto saveOrderItem(OrderItemDto item) {
        OrderItem orderItem = mapper.toOrderItem(item);
        return mapper.toOrderItemDto(repository.save(orderItem));
    }

    @Override
    public Optional<OrderItemDto> getOrderItem(Integer itemId) {
        return repository.findById(itemId).map(mapper::toOrderItemDto);
    }

    @Override
    public void deleteOrderItem(Integer itemId) {
        repository.deleteById(itemId);
    }
}
