package com.jiltsa.admin.orders.controller;

import com.jiltsa.admin.orders.domain.dto.OrderDto;
import com.jiltsa.admin.orders.domain.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("jiltsa/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @GetMapping
    public List<OrderDto> getActiveOrders(){
        return service.getActiveOrders();
    }

    @PostMapping
    public OrderDto saveOrder(@RequestBody OrderDto orderDto){
        return service.saveOrder(orderDto);
    }

    @GetMapping("/{orderId}")
    public Optional<OrderDto> getOrder(@PathVariable("orderId") Integer orderId){
        return service.getOrder(orderId);
    }

    @PutMapping
    public OrderDto updateOrder(@RequestBody OrderDto orderDto){
        return service.saveOrder(orderDto);
    }
}
