package com.jiltsa.admin.cashproof.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.cashproof.domain.dto.CashSortingDto;
import com.jiltsa.admin.cashproof.domain.service.CashSortingService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("jiltsa/api/v1/cash-sorting")
@RequiredArgsConstructor
public class CashSortingController {
    private final CashSortingService service;

    @Operation(summary = "Get the cash sorting of an accounting", description = "Count of bills and coins by denomination.")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "The accounting has no cash sorting")
    @GetMapping("/accounting/{accountingId}")
    public CashSortingDto getCashSorting(@Parameter(description = "Accounting id") @PathVariable("accountingId") Integer accountingId){
        return service.getCashSorting(accountingId)
                .orElseThrow(() -> new ResourceNotFoundException("CashSorting", accountingId));
    }

    @Operation(summary = "Save the cash sorting of an accounting")
    @PostMapping()
    public CashSortingDto saveCashSorting(@Valid @RequestBody CashSortingDto cashSortingDto){
        return service.saveCashSorting(cashSortingDto);
    }
}
