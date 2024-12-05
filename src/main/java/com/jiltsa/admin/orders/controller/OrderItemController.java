package com.jiltsa.admin.orders.controller;

import com.jiltsa.admin.orders.domain.dto.OrderItemDto;
import com.jiltsa.admin.orders.domain.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("jiltsa/api/v1/order-items")
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemService service;

    @GetMapping("/{itemId}")
    public Optional<OrderItemDto> getItem(@PathVariable("itemId") Integer itemId){
        return service.getOrderItem(itemId);
    }

    @PostMapping
    public OrderItemDto saveItem(@RequestBody OrderItemDto itemDto){
        return service.saveOrderItem(itemDto);
    }

    @PostMapping("/save-all")
    public List<OrderItemDto> saveAll(@RequestBody List<OrderItemDto> itemDtoList){
        return service.saveOrderItemDtoList(itemDtoList);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable("itemId") Integer itemId){
        service.deleteOrderItem(itemId);
    }

    @PutMapping
    public OrderItemDto updateItem(@RequestBody OrderItemDto itemDto){
        return service.saveOrderItem(itemDto);
    }

    @PutMapping("/update-all")
    public List<OrderItemDto> updateAll(@RequestBody List<OrderItemDto> itemDtoList){
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
