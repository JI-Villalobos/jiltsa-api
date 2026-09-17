package com.jiltsa.admin.orders.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.orders.domain.dto.OrderItemDto;
import com.jiltsa.admin.orders.domain.service.OrderItemService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/order-items")
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemService service;

    @GetMapping("/{itemId}")
    public OrderItemDto getItem(@PathVariable("itemId") Integer itemId){
        return service.getOrderItem(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem", itemId));
    }

    @PostMapping
    public OrderItemDto saveItem(@Valid @RequestBody OrderItemDto itemDto){
        return service.saveOrderItem(itemDto);
    }

    @PostMapping("/save-all")
    public List<OrderItemDto> saveAll(@RequestBody List<@Valid OrderItemDto> itemDtoList){
        return service.saveOrderItemDtoList(itemDtoList);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable("itemId") Integer itemId){
        service.deleteOrderItem(itemId);
    }

    @PutMapping
    public OrderItemDto updateItem(@Valid @RequestBody OrderItemDto itemDto){
        return service.saveOrderItem(itemDto);
    }

    @PutMapping("/update-all")
    public List<OrderItemDto> updateAll(@RequestBody List<@Valid OrderItemDto> itemDtoList){
        return service.saveOrderItemDtoList(itemDtoList);
    }

    @PutMapping("/disable/{orderId}")
    public void disableItems(
            @PathVariable("orderId") Integer orderId,
            @RequestParam int status
    ){
        service.disableItems(orderId, status);
    }
}
