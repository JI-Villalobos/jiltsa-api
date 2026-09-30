package com.jiltsa.admin.operativity.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.operativity.domain.dto.AverageSalesResultDto;
import com.jiltsa.admin.operativity.domain.dto.SaleResultDto;
import com.jiltsa.admin.operativity.domain.service.SaleResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/sale-results")
@RequiredArgsConstructor
public class SaleResultController {
    private final SaleResultService service;

    @Operation(summary = "List a branch's weekly sale results from the last month")
    @GetMapping("/latest/{branchId}")
    public List<SaleResultDto> getResults(@Parameter(description = "Branch id") @PathVariable("branchId") Integer branchId){
        return service.findByBranch(branchId);
    }

    @Operation(summary = "List a branch's weekly sale results starting within a date range")
    @GetMapping("/by-period")
    public List<SaleResultDto> getResultsByPeriod(
            @Parameter(description = "Branch id") @RequestParam Integer branchId,
            @Parameter(description = "Range start", example = "2026-03-01T00:00:00") @RequestParam LocalDateTime initialDate,
            @Parameter(description = "Range end", example = "2026-03-31T23:59:59") @RequestParam LocalDateTime finalDate
            ){
        return service.findByBranchAndDateRange(branchId, initialDate, finalDate);
    }

    @Operation(summary = "Save a weekly sale result")
    @PostMapping
    public SaleResultDto saveResult(@Valid @RequestBody SaleResultDto saleResultDto){
        return service.saveResult(saleResultDto);
    }
}
