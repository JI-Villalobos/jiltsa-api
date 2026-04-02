package com.jiltsa.admin.operativity.controller;

import com.jiltsa.admin.operativity.domain.dto.AverageSalesResultDto;
import com.jiltsa.admin.operativity.domain.dto.SaleResultDto;
import com.jiltsa.admin.operativity.domain.service.SaleResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/sale-results")
@RequiredArgsConstructor
public class SaleResultController {
    private final SaleResultService service;

    @GetMapping("/latest/{branchId}")
    public List<SaleResultDto> getResults(@PathVariable("branchId") Integer branchId){
        return service.findByBranch(branchId);
    }

    @GetMapping("/by-period")
    public List<SaleResultDto> getResultsByPeriod(
            @RequestParam Integer branchId,
            @RequestParam LocalDateTime initialDate,
            @RequestParam LocalDateTime finalDate
            ){
        return service.findByBranchAndDateRange(branchId, initialDate, finalDate);
    }

    @PostMapping
    public SaleResultDto saveResult(@RequestBody SaleResultDto saleResultDto){
        return service.saveResult(saleResultDto);
    }
}
