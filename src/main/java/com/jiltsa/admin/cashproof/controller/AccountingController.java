package com.jiltsa.admin.cashproof.controller;

import com.jiltsa.admin.cashproof.domain.dto.AccountingDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateAccountingDto;
import com.jiltsa.admin.cashproof.domain.service.AccountingDService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountingController {
    private final AccountingDService service;
    @GetMapping("/{branchId}")
    public List<AccountingDto> getAccountingRegistries(@PathVariable("branchId") Integer id){
        return service.getLastAccountingRegistries(id);
    }

    @GetMapping("/{initial}/{end}/{branchId}")
    public List<AccountingDto> getAccountingRegistriesBetweenDates(
            @PathVariable("initial")LocalDateTime initial,
            @PathVariable("end") LocalDateTime end,
            @PathVariable("branchId") Integer branchId
            ){
        return service.getAccountingRegistriesBetweenTwoDates(initial, end, branchId);
    }

    @PostMapping
    public CreateAccountingDto createAccounting(@RequestBody CreateAccountingDto createAccountingDto){
        return service.createAccounting(createAccountingDto);
    }
}
