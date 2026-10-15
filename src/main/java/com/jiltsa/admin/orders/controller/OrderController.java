package com.jiltsa.admin.orders.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.orders.domain.dto.OrderDto;
import com.jiltsa.admin.orders.domain.service.OrderService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import com.jiltsa.admin.security.AdminOnly;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @Operation(summary = "List open orders of all branches")
    @GetMapping
    public List<OrderDto> getActiveOrders(){
        return service.getActiveOrders();
    }

    @Operation(summary = "Create an order")
    @PostMapping
    public OrderDto saveOrder(@Valid @RequestBody OrderDto orderDto){
        return service.saveOrder(orderDto);
    }

    @Operation(summary = "Get an order")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No order with that id")
    @GetMapping("/{orderId}")
    public OrderDto getOrder(@Parameter(description = "Order id") @PathVariable("orderId") Integer orderId){
        return service.getOrder(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));
    }

    @Operation(summary = "Update an order")
    @PutMapping
    public OrderDto updateOrder(@Valid @RequestBody OrderDto orderDto){
        return service.saveOrder(orderDto);
    }

    @Operation(summary = "Delete an order")
    @AdminOnly
    @DeleteMapping("/{orderId}")
    public void deleteOrder(@Parameter(description = "Order id") @PathVariable("orderId") Integer orderId){
        service.deleteOrder(orderId);
    }

    @Operation(summary = "List a branch's open orders")
    @GetMapping("/by-branch/{branchId}")
    public List<OrderDto> getActiveOrdersByBranch(@Parameter(description = "Branch id") @PathVariable("branchId") Integer branchId){
        return service.getActiveOrdersByBranch(branchId);
    }
}
