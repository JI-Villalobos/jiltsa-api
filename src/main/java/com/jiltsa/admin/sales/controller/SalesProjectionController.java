package com.jiltsa.admin.sales.controller;

import com.jiltsa.admin.sales.domain.dto.*;
import com.jiltsa.admin.sales.projections.SalesProjectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("jiltsa/api/v1/sales-projections")
@RequiredArgsConstructor
public class SalesProjectionController {
    private final SalesProjectionService salesProjectionService;

    @Operation(summary = "Summarize a branch's sales within a date range", description = "Totals by category and by user, and the five best-selling medicines by quantity.")
    @GetMapping
    public SalesProjectionDto getSalesProjection(
            @Parameter(description = "Branch id") @RequestParam Integer branchId,
            @Parameter(description = "Range start", example = "2026-03-01T00:00:00") @RequestParam LocalDateTime initialDate,
            @Parameter(description = "Range end", example = "2026-03-31T23:59:59") @RequestParam LocalDateTime finalDate
            ){
        return salesProjectionService.getSalesProjection(branchId, initialDate, finalDate);
    }

    @Operation(summary = "Summarize each active branch's sales per day over the last 30 days",
            description = "Excludes 'TA' lines. Days without uploaded sales are marked pending; the average only counts days with sales.")
    @GetMapping("/daily")
    public DailySalesProjectionDto getDailySales() {
        return salesProjectionService.getDailySales();
    }

    @Operation(summary = "Total a branch's sales within a date range")
    @GetMapping("/total-sales")
    public TotalSalesDto getTotalSales(
            @Parameter(description = "Branch id") @RequestParam Integer branchId,
            @Parameter(description = "Range start", example = "2026-03-01T00:00:00") @RequestParam LocalDateTime initialDate,
            @Parameter(description = "Range end", example = "2026-03-31T23:59:59") @RequestParam LocalDateTime finalDate
    ) {
        return salesProjectionService.getTotalSales(branchId, initialDate, finalDate);
    }

    @Operation(summary = "Total a branch's pharmacy sales and utility within a date range", description = "Medicine, healing material and vitamin categories.")
    @GetMapping("/pharmacy")
    public PharmacySalesResumeDto getPharmacyResume(
            @Parameter(description = "Branch id") @RequestParam Integer branchId,
            @Parameter(description = "Range start", example = "2026-03-01T00:00:00") @RequestParam LocalDateTime initialDate,
            @Parameter(description = "Range end", example = "2026-03-31T23:59:59") @RequestParam LocalDateTime finalDate
    ){
        return salesProjectionService.getPharmacySales(branchId, initialDate, finalDate);
    }

    @Operation(summary = "Summarize a branch's pharmacy sales by category within a date range")
    @GetMapping("/budget")
    public Map<String, SaleSummaryDto> getBudget(
            @Parameter(description = "Branch id") @RequestParam Integer branchId,
            @Parameter(description = "Range start", example = "2026-03-01T00:00:00") @RequestParam LocalDateTime initialDate,
            @Parameter(description = "Range end", example = "2026-03-31T23:59:59") @RequestParam LocalDateTime finalDate
    ){
        return salesProjectionService.computeBudget(branchId, initialDate, finalDate);
    }

    @Operation(summary = "List consignment sales of all branches within a date range")
    @GetMapping("/consignment")
    public List<ConsignmentSaleDto> getConsignmentReport(
            @Parameter(description = "Range start", example = "2026-03-01T00:00:00") @RequestParam LocalDateTime initialDate,
            @Parameter(description = "Range end", example = "2026-03-31T23:59:59") @RequestParam LocalDateTime finalDate
    ){
        return salesProjectionService.consignmentSalesReport(initialDate, finalDate);
    }
}
