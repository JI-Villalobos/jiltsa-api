package com.jiltsa.admin.promotions.controller;

import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import com.jiltsa.admin.promotions.domain.dto.ExpirationLossProjectionDto;
import com.jiltsa.admin.promotions.domain.dto.RecordingLossExpirationDto;
import com.jiltsa.admin.promotions.domain.service.RecordingLossExpirationService;
import com.jiltsa.admin.promotions.projection.ExpirationLossProjectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/** Loss records are created by POST /promotions/{id}/close; this controller only reads them. */
@RestController
@RequestMapping("jiltsa/api/v1/expiration-losses")
@RequiredArgsConstructor
public class RecordingLossExpirationController {
    private final RecordingLossExpirationService service;
    private final ExpirationLossProjectionService projectionService;

    @Operation(summary = "Get an expiration loss record")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No record with that id")
    @GetMapping("/{recordId}")
    public RecordingLossExpirationDto getRecord(@Parameter(description = "Record id") @PathVariable("recordId") Integer recordId) {
        return service.getRecord(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("RecordingLossExpiration", recordId));
    }

    @Operation(summary = "Get the expiration loss recorded for a promotion")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "The promotion has no loss recorded")
    @GetMapping("/by-promotion/{promotionId}")
    public RecordingLossExpirationDto getRecordByPromotion(
            @Parameter(description = "Promotion id") @PathVariable("promotionId") Integer promotionId) {
        return service.getRecordByPromotion(promotionId)
                .orElseThrow(() -> new ResourceNotFoundException("RecordingLossExpiration for promotion", promotionId));
    }

    @Operation(summary = "List a branch's expiration losses recorded within a date range", description = "Newest first.")
    @GetMapping("/by-branch/{branchId}")
    public List<RecordingLossExpirationDto> getRecordsByBranch(
            @Parameter(description = "Branch id") @PathVariable("branchId") Integer branchId,
            @Parameter(description = "Range start (record date)", example = "2026-11-01") @RequestParam LocalDate initialDate,
            @Parameter(description = "Range end (record date)", example = "2026-11-30") @RequestParam LocalDate finalDate) {
        return service.getRecordsByBranch(branchId, initialDate, finalDate);
    }

    @Operation(summary = "Project the expiration losses recorded within a date range",
            description = "Total loss of the range plus each promotion's loss broken down into its medicines (quantity x purchasePrice). Without branchId, every branch.")
    @GetMapping("/projection")
    public ExpirationLossProjectionDto getProjection(
            @Parameter(description = "Branch id; omit for every branch") @RequestParam(required = false) Integer branchId,
            @Parameter(description = "Range start (record date)", example = "2026-11-01") @RequestParam LocalDate initialDate,
            @Parameter(description = "Range end (record date)", example = "2026-11-30") @RequestParam LocalDate finalDate) {
        return projectionService.getExpirationLosses(branchId, initialDate, finalDate);
    }
}
