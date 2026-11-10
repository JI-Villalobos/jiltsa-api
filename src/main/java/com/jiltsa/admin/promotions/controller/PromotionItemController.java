package com.jiltsa.admin.promotions.controller;

import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import com.jiltsa.admin.promotions.domain.dto.PromotionItemDto;
import com.jiltsa.admin.promotions.domain.service.PromotionItemService;
import com.jiltsa.admin.security.AdminOnly;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/promotion-items")
@RequiredArgsConstructor
public class PromotionItemController {
    static final int MAX_BATCH_SIZE = 500;

    private final PromotionItemService service;

    @Operation(summary = "Get a promotion item")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No promotion item with that id")
    @GetMapping("/{itemId}")
    public PromotionItemDto getItem(@Parameter(description = "Promotion item id") @PathVariable("itemId") Long itemId) {
        return service.getItem(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("PromotionItem", itemId));
    }

    @Operation(summary = "List a promotion's items", description = "Soonest to expire first.")
    @GetMapping("/by-promotion/{promotionId}")
    public List<PromotionItemDto> getItemsByPromotion(
            @Parameter(description = "Promotion id") @PathVariable("promotionId") Integer promotionId) {
        return service.getItemsByPromotion(promotionId);
    }

    @Operation(summary = "Add an item to an active promotion")
    @ApiResponse(responseCode = "201", description = "Created", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No promotion with that id")
    @ApiResponse(responseCode = "409", description = "The promotion is closed")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PromotionItemDto createItem(@Valid @RequestBody PromotionItemDto itemDto) {
        return service.createItem(itemDto);
    }

    @Operation(summary = "Add a batch of items to active promotions", description = "All or nothing, at most 500 items; they may belong to different promotions.")
    @ApiResponse(responseCode = "201", description = "Created", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "A promotion in the batch does not exist")
    @ApiResponse(responseCode = "409", description = "A promotion in the batch is closed")
    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public List<PromotionItemDto> createItems(
            @RequestBody @NotEmpty @Size(max = MAX_BATCH_SIZE) List<@Valid @NotNull PromotionItemDto> items) {
        return service.createItems(items);
    }

    @Operation(summary = "Update a promotion item", description = "Lower quantity as the medicine sells so the loss recorded at closing is what was left.")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No promotion item or promotion with that id")
    @ApiResponse(responseCode = "409", description = "The promotion is closed")
    @PutMapping("/{itemId}")
    public PromotionItemDto updateItem(
            @Parameter(description = "Promotion item id") @PathVariable("itemId") Long itemId,
            @Valid @RequestBody PromotionItemDto itemDto) {
        return service.updateItem(itemId, itemDto);
    }

    @Operation(summary = "Delete a promotion item")
    @ApiResponse(responseCode = "204", description = "Deleted")
    @ApiResponse(responseCode = "404", description = "No promotion item with that id")
    @ApiResponse(responseCode = "409", description = "The promotion is closed")
    @AdminOnly
    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@Parameter(description = "Promotion item id") @PathVariable("itemId") Long itemId) {
        service.deleteItem(itemId);
    }
}
