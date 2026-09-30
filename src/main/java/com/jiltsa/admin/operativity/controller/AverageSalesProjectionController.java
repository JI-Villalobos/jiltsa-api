package com.jiltsa.admin.operativity.controller;

import com.jiltsa.admin.operativity.domain.dto.AverageSalesResultDto;
import com.jiltsa.admin.operativity.projection.AverageSalesProjectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("jiltsa/api/v1/average-sales")
@RequiredArgsConstructor
public class AverageSalesProjectionController {
    private final AverageSalesProjectionService service;

    @Operation(summary = "Get the average sales of a branch", description = "Computed from its weekly sale results.")
    @GetMapping("/branch/{branchId}")
    public AverageSalesResultDto getAverageSales(@Parameter(description = "Branch id") @PathVariable("branchId") Integer branchId){
        return service.getAverageSales(branchId);
    }
}
