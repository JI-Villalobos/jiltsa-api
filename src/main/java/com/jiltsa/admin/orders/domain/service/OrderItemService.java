package com.jiltsa.admin.orders.domain.service;

import com.jiltsa.admin.orders.domain.dto.OrderItemDto;
import com.jiltsa.admin.orders.persistence.entity.OrderItem;
import com.jiltsa.admin.orders.persistence.mapper.OrderItemMapper;
import com.jiltsa.admin.orders.persistence.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository repository;
    private final OrderItemMapper mapper;

    @Transactional
    public OrderItemDto saveOrderItem(OrderItemDto item) {
        OrderItem orderItem = mapper.toOrderItem(item);
        return mapper.toOrderItemDto(repository.save(orderItem));
    }

    @Transactional
    public List<OrderItemDto> saveOrderItemDtoList(List<OrderItemDto> items) {
        List<OrderItem> itemList = mapper.toOrderItemList(items);
        return mapper.toOrderItemDtoList(repository.saveAll(itemList));
    }

    public Optional<OrderItemDto> getOrderItem(Integer itemId) {
        return repository.findById(itemId).map(mapper::toOrderItemDto);
    }

    @Transactional
    public void deleteOrderItem(Integer itemId) {
        repository.deleteById(itemId);
    }

    @Transactional
    public void disableItems(Integer orderId, Integer status) {
        int currentStatus = status == 4 ? 1 : 2;
        List<OrderItem> itemList = repository.findByOrderId(orderId).stream()
                .filter(item -> item.getStatus().equals(currentStatus)).toList();
        List<OrderItem> disabled = itemList.stream().map(item -> new OrderItem(
                item.getId(), item.getOrderId(), item.getItem(), item.getRequested(),
                item.getPrice(), item.getBudgeted(), item.getItemType(), item.getStocked(),
                item.getFinalPrice(), item.getTotal(), status
        )).toList();

        disabled.forEach(repository::save);
    }
}
