package com.jiltsa.admin.cashproof.controller;

import com.jiltsa.admin.cashproof.domain.dto.CashSortingDto;
import com.jiltsa.admin.cashproof.domain.service.CashSortingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("jiltsa/api/v1/cash-sorting")
@RequiredArgsConstructor
public class CashSortingController {
    private final CashSortingService service;

    @GetMapping("/accounting/{accountingId}")
    public Optional<CashSortingDto> getCashSorting(@PathVariable("accountingId") Integer accountingId){
        return service.getCashSorting(accountingId);
    }

    @PostMapping()
    public CashSortingDto saveCashSorting(@RequestBody CashSortingDto cashSortingDto){
        return service.saveCashSorting(cashSortingDto);
    }
}
