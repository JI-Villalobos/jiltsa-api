package com.jiltsa.admin.orders.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.orders.domain.dto.OrderDto;
import com.jiltsa.admin.orders.domain.service.OrderService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public OrderDto saveOrder(@Valid @RequestBody OrderDto orderDto){
        return service.saveOrder(orderDto);
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrder(@PathVariable("orderId") Integer orderId){
        return service.getOrder(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));
    }

    @PutMapping
    public OrderDto updateOrder(@Valid @RequestBody OrderDto orderDto){
        return service.saveOrder(orderDto);
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable("orderId") Integer orderId){
        service.deleteOrder(orderId);
    }

    @GetMapping("/by-branch/{branchId}")
    public List<OrderDto> getActiveOrdersByBranch(@PathVariable("branchId") Integer branchId){
        return service.getActiveOrdersByBranch(branchId);
    }
}
