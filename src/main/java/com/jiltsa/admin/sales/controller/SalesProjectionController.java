package com.jiltsa.admin.sales.controller;

import com.jiltsa.admin.sales.domain.dto.PharmacySalesResumeDto;
import com.jiltsa.admin.sales.domain.dto.SaleSummaryDto;
import com.jiltsa.admin.sales.domain.dto.SalesProjectionDto;
import com.jiltsa.admin.sales.domain.dto.TotalSalesDto;
import com.jiltsa.admin.sales.projections.SalesProjectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("jiltsa/api/v1/sales-projections")
@RequiredArgsConstructor
public class SalesProjectionController {
    private final SalesProjectionService salesProjectionService;

    @GetMapping
    public SalesProjectionDto getSalesProjection(
            @RequestParam Integer branchId,
            @RequestParam LocalDateTime initialDate,
            @RequestParam LocalDateTime finalDate
            ){
        return salesProjectionService.getSalesProjection(branchId, initialDate, finalDate);
    }

    @GetMapping("/total-sales")
    public TotalSalesDto getTotalSales(
            @RequestParam Integer branchId,
            @RequestParam LocalDateTime initialDate,
            @RequestParam LocalDateTime finalDate
    ) {
        return salesProjectionService.getTotalSales(branchId, initialDate, finalDate);
    }

    @GetMapping("/pharmacy")
    public PharmacySalesResumeDto getPharmacyResume(
            @RequestParam Integer branchId,
            @RequestParam LocalDateTime initialDate,
            @RequestParam LocalDateTime finalDate
    ){
        return salesProjectionService.getPharmacySales(branchId, initialDate, finalDate);
    }

    @GetMapping("/budget")
    public Map<String, SaleSummaryDto> getBudget(
            @RequestParam Integer branchId,
            @RequestParam LocalDateTime initialDate,
            @RequestParam LocalDateTime finalDate
    ){
        return salesProjectionService.computeBudget(branchId, initialDate, finalDate);
    }
}
