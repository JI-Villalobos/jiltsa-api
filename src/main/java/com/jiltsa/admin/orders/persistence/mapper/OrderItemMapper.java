package com.jiltsa.admin.orders.persistence.mapper;

import com.jiltsa.admin.orders.domain.dto.OrderItemDto;
import com.jiltsa.admin.orders.persistence.entity.OrderItem;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemDto toOrderItemDto(OrderItem orderItem);
    List<OrderItemDto> toOrderItemDtoList(List<OrderItem> itemList);

    @InheritInverseConfiguration
    @Mapping(target = "order", ignore = true)
    OrderItem toOrderItem(OrderItemDto itemDto);

    @InheritInverseConfiguration
    @Mapping(target = "order", ignore = true)
    List<OrderItem> toOrderItemList(List<OrderItemDto> orderItemDtoList);

}
