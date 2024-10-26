package com.jiltsa.admin.orders.persistence.mapper;

import com.jiltsa.admin.orders.domain.dto.OrderDto;
import com.jiltsa.admin.orders.persistence.entity.Order;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toOrderDto(Order order);
    List<OrderDto> toOrderDtoList(List<Order> orders);

    @InheritInverseConfiguration
    Order toOrder(OrderDto orderDto);

    @InheritInverseConfiguration
    List<Order> toOrderList(List<OrderDto> orderDtoList);
}
