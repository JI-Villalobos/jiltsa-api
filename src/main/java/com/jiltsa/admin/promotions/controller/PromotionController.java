package com.jiltsa.admin.promotions.controller;

import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import com.jiltsa.admin.promotions.domain.dto.PromotionDto;
import com.jiltsa.admin.promotions.domain.dto.RecordingLossExpirationDto;
import com.jiltsa.admin.promotions.domain.service.PromotionService;
import com.jiltsa.admin.security.AdminOnly;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/promotions")
@RequiredArgsConstructor
public class PromotionController {
    private final PromotionService service;

    @Operation(summary = "Get a promotion with its items")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No promotion with that id")
    @GetMapping("/{promotionId}")
    public PromotionDto getPromotion(@Parameter(description = "Promotion id") @PathVariable("promotionId") Integer promotionId) {
        return service.getPromotion(promotionId)
                .orElseThrow(() -> new ResourceNotFoundException("Promotion", promotionId));
    }

    @Operation(summary = "List a branch's promotions created within a date range", description = "Newest first, with their items.")
    @GetMapping("/by-branch/{branchId}")
    public List<PromotionDto> getPromotionsByBranch(
            @Parameter(description = "Branch id") @PathVariable("branchId") Integer branchId,
            @Parameter(description = "Range start (creation date)", example = "2026-11-01") @RequestParam LocalDate initialDate,
            @Parameter(description = "Range end (creation date)", example = "2026-11-30") @RequestParam LocalDate finalDate) {
        return service.getPromotionsByBranch(branchId, initialDate, finalDate);
    }

    @Operation(summary = "List a branch's active promotions", description = "Soonest to end first, with their items.")
    @GetMapping("/by-branch/{branchId}/active")
    public List<PromotionDto> getActivePromotionsByBranch(
            @Parameter(description = "Branch id") @PathVariable("branchId") Integer branchId) {
        return service.getActivePromotionsByBranch(branchId);
    }

    @Operation(summary = "Create a promotion", description = "Created active, dated today unless creationDate is given. Add its items through /promotion-items.")
    @ApiResponse(responseCode = "201", description = "Created", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No branch with that id")
    @ApiResponse(responseCode = "409", description = "promotionEndDate is before creationDate")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PromotionDto createPromotion(@Valid @RequestBody PromotionDto promotionDto) {
        return service.createPromotion(promotionDto);
    }

    @Operation(summary = "Update a promotion", description = "Name, branch, dates and active flag; items are not touched.")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No promotion or branch with that id")
    @ApiResponse(responseCode = "409", description = "Reactivating a promotion whose loss is recorded, or promotionEndDate before creationDate")
    @PutMapping("/{promotionId}")
    public PromotionDto updatePromotion(
            @Parameter(description = "Promotion id") @PathVariable("promotionId") Integer promotionId,
            @Valid @RequestBody PromotionDto promotionDto) {
        return service.updatePromotion(promotionId, promotionDto);
    }

    @Operation(summary = "Close a promotion and record its expiration loss",
            description = "Deactivates the promotion and records, for its branch, the value at purchase price of the items left (quantity x purchasePrice). Done once per promotion.")
    @ApiResponse(responseCode = "201", description = "Loss recorded", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No promotion with that id")
    @ApiResponse(responseCode = "409", description = "The promotion is already closed")
    @PostMapping("/{promotionId}/close")
    @ResponseStatus(HttpStatus.CREATED)
    public RecordingLossExpirationDto closePromotion(
            @Parameter(description = "Promotion id") @PathVariable("promotionId") Integer promotionId) {
        return service.closePromotion(promotionId);
    }

    @Operation(summary = "Delete a promotion and its items", description = "Not allowed once its loss is recorded.")
    @ApiResponse(responseCode = "204", description = "Deleted")
    @ApiResponse(responseCode = "404", description = "No promotion with that id")
    @ApiResponse(responseCode = "409", description = "The promotion has its loss recorded")
    @AdminOnly
    @DeleteMapping("/{promotionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePromotion(@Parameter(description = "Promotion id") @PathVariable("promotionId") Integer promotionId) {
        service.deletePromotion(promotionId);
    }
}
