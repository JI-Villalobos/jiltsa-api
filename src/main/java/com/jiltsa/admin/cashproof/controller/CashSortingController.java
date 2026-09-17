package com.jiltsa.admin.cashproof.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.cashproof.domain.dto.CashSortingDto;
import com.jiltsa.admin.cashproof.domain.service.CashSortingService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("jiltsa/api/v1/cash-sorting")
@RequiredArgsConstructor
public class CashSortingController {
    private final CashSortingService service;

    @GetMapping("/accounting/{accountingId}")
    public CashSortingDto getCashSorting(@PathVariable("accountingId") Integer accountingId){
        return service.getCashSorting(accountingId)
                .orElseThrow(() -> new ResourceNotFoundException("CashSorting", accountingId));
    }

    @PostMapping()
    public CashSortingDto saveCashSorting(@Valid @RequestBody CashSortingDto cashSortingDto){
        return service.saveCashSorting(cashSortingDto);
    }
}
