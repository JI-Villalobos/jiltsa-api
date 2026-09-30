package com.jiltsa.admin.cashproof.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.cashproof.domain.dto.CreditSaleBalanceDto;
import com.jiltsa.admin.cashproof.domain.dto.CreditSaleDto;
import com.jiltsa.admin.cashproof.domain.service.CreditSaleService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/credit-sale")
@RequiredArgsConstructor
public class CreditSaleController {
    private final CreditSaleService service;

    @Operation(summary = "Get a credit sale")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No credit sale with that id")
    @GetMapping("/get/{creditSaleId}")
    public CreditSaleDto getCreditSale(@Parameter(description = "Credit sale id") @PathVariable("creditSaleId") Integer creditSaleId){
        return service.getCreditSale(creditSaleId)
                .orElseThrow(() -> new ResourceNotFoundException("CreditSale", creditSaleId));
    }

    @Operation(summary = "List all credit sales of a branch")
    @GetMapping("/get-all/{branchId}")
    public List<CreditSaleDto> getCreditSales(@Parameter(description = "Branch id") @PathVariable("branchId") Integer branchId){
        return service.getCreditSales(branchId);
    }

    @Operation(summary = "List a branch's credit sales by payment status")
    @GetMapping("/get-by-status/{branchId}/{isPaid}")
    public List<CreditSaleDto> getCreditSalesByPaymentStatus(@Parameter(description = "Branch id") @PathVariable("branchId") Integer branchId, @Parameter(description = "true for paid sales, false for pending ones") @PathVariable("isPaid") Boolean isPaid){
        return service.getCreditSaleByPaymentStatus(branchId, isPaid);
    }

    @Operation(summary = "Get the balance of a credit sale", description = "Number of partial payments, amount paid and amount outstanding.")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No credit sale with that id")
    @GetMapping("/balance/{creditSaleId}")
    public CreditSaleBalanceDto getCreditSaleBalance(@Parameter(description = "Credit sale id") @PathVariable("creditSaleId") Integer creditSaleId){
        return service.getCreditSaleBalance(creditSaleId)
                .orElseThrow(() -> new ResourceNotFoundException("CreditSaleBalance", creditSaleId));
    }

    @Operation(summary = "Register a credit sale", description = "Starts unpaid.")
    @PostMapping
    public CreditSaleDto createCreditSale(@Valid @RequestBody CreditSaleDto creditSaleDto){
        return service.createCreditSale(creditSaleDto);
    }

    @Operation(summary = "Update a credit sale")
    @PutMapping
    public CreditSaleDto updateCreditSale(@Valid @RequestBody CreditSaleDto creditSaleDto){
        return service.updateCreditSale(creditSaleDto);
    }

}
