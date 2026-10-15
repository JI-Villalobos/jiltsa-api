package com.jiltsa.admin.orders.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.orders.domain.dto.OrderItemDto;
import com.jiltsa.admin.orders.domain.service.OrderItemService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import com.jiltsa.admin.security.AdminOnly;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/order-items")
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemService service;

    @Operation(summary = "Get an order item")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No order item with that id")
    @GetMapping("/{itemId}")
    public OrderItemDto getItem(@Parameter(description = "Order item id") @PathVariable("itemId") Integer itemId){
        return service.getOrderItem(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem", itemId));
    }

    @Operation(summary = "Create an order item")
    @PostMapping
    public OrderItemDto saveItem(@Valid @RequestBody OrderItemDto itemDto){
        return service.saveOrderItem(itemDto);
    }

    @Operation(summary = "Create several order items")
    @PostMapping("/save-all")
    public List<OrderItemDto> saveAll(@RequestBody List<@Valid OrderItemDto> itemDtoList){
        return service.saveOrderItems(itemDtoList);
    }

    @Operation(summary = "Delete an order item")
    @AdminOnly
    @DeleteMapping("/{itemId}")
    public void deleteItem(@Parameter(description = "Order item id") @PathVariable("itemId") Integer itemId){
        service.deleteOrderItem(itemId);
    }

    @Operation(summary = "Update an order item")
    @PutMapping
    public OrderItemDto updateItem(@Valid @RequestBody OrderItemDto itemDto){
        return service.saveOrderItem(itemDto);
    }

    @Operation(summary = "Update several order items")
    @PutMapping("/update-all")
    public List<OrderItemDto> updateAll(@RequestBody List<@Valid OrderItemDto> itemDtoList){
        return service.saveOrderItems(itemDtoList);
    }

    @Operation(summary = "Move an order's items to a new status", description = "With status 4, items currently at status 1 move to 4; with any other status, items at status 2 move to it.")
    @PutMapping("/disable/{orderId}")
    public void disableItems(
            @Parameter(description = "Order id") @PathVariable("orderId") Integer orderId,
            @Parameter(description = "New status for the items") @RequestParam int status
    ){
        service.disableItems(orderId, status);
    }
}
