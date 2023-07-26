package com.jiltsa.admin.cashproof.controller;

import com.jiltsa.admin.cashproof.domain.dto.AccountingDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateAccountingDto;
import com.jiltsa.admin.cashproof.domain.dto.CustomAccountingDto;
import com.jiltsa.admin.cashproof.domain.service.AccountingDService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("jiltsa/api/v1/accounts")
@RequiredArgsConstructor
public class AccountingController {
    private final AccountingDService service;

    @GetMapping("/{branchId}")
    public List<AccountingDto> getLatestAccountingRegistries(@PathVariable("branchId") Integer id){
        return service.getLastAccountingRegistries(id);
    }

    @GetMapping("/account/{accountingId}")
    public Optional<AccountingDto> getAccounting(@PathVariable("accountingId") Integer id){
        return service.getAccounting(id);
    }

    @GetMapping("/{initial}/{end}/{branchId}")
    //jiltsa/api/v1/accounts/2023-03-25T18:29:08.608983/2023-03-29T18:29:08.608983/1
    public List<AccountingDto> getAccountingRegistriesBetweenDates(
            @PathVariable("initial")LocalDateTime initial,
            @PathVariable("end") LocalDateTime end,
            @PathVariable("branchId") Integer branchId){
        return service.getAccountingRegistriesBetweenTwoDates(initial, end, branchId);
    }

    @PostMapping
    public CreateAccountingDto createAccounting(@RequestBody CreateAccountingDto createAccountingDto){
        return service.createAccounting(createAccountingDto);
    }

    @PostMapping("/out-of-date")
    public CustomAccountingDto createOutOfDateAccounting(@RequestBody CustomAccountingDto customAccountingDto){
        return service.createOutOfDateAccounting(customAccountingDto);
    }
}
