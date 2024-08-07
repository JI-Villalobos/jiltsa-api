package com.jiltsa.admin.cashproof.controller;

import com.jiltsa.admin.cashproof.domain.dto.CreditSaleBalanceDto;
import com.jiltsa.admin.cashproof.domain.dto.CreditSaleDto;
import com.jiltsa.admin.cashproof.domain.service.CreditSaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("jiltsa/api/v1/credit-sale")
@RequiredArgsConstructor
public class CreditSaleController {
    private final CreditSaleService service;

    @GetMapping("/get/{creditSaleId}")
    public Optional<CreditSaleDto> getCreditSale(@PathVariable("creditSaleId") Integer creditSaleId){
        return service.getCreditSale(creditSaleId);
    }

    @GetMapping("/get-all/{branchId}")
    public List<CreditSaleDto> getCreditSales(@PathVariable("branchId") Integer branchId){
        return service.getCreditSales(branchId);
    }

    @GetMapping("/get-by-status/{branchId}/{isPaid}")
    public List<CreditSaleDto> getCreditSalesByPaymentStatus(@PathVariable("branchId") Integer branchId, @PathVariable("isPaid") Boolean isPaid){
        return service.getCreditSaleByPaymentStatus(branchId, isPaid);
    }

    @GetMapping("/balance/{creditSaleId}")
    public Optional<CreditSaleBalanceDto> getCreditSaleBalance(@PathVariable("creditSaleId") Integer creditSaleId){
        return service.getCreditSaleBalance(creditSaleId);
    }

    @PostMapping
    public CreditSaleDto createCreditSale(@RequestBody CreditSaleDto creditSaleDto){
        return service.createCreditSale(creditSaleDto);
    }

    @PutMapping
    public CreditSaleDto updateCreditSale(@RequestBody CreditSaleDto creditSaleDto){
        return service.updateCreditSale(creditSaleDto);
    }

}
