package com.jiltsa.admin.cashproof.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.cashproof.domain.dto.AccountingDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateAccountingDto;
import com.jiltsa.admin.cashproof.domain.dto.CustomAccountingDto;
import com.jiltsa.admin.cashproof.domain.service.AccountingDService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/accounts")
@RequiredArgsConstructor
public class AccountingController {
    private final AccountingDService service;

    @GetMapping
    public Page<AccountingDto> getLastesAccountingRegitriesAllBranches(
            @PageableDefault(size = 12, sort = "date") Pageable pageable
    ){
        return service.getLastAccountingRegistriesAllBranches(pageable);
    }

    @GetMapping("/{branchId}")
    public List<AccountingDto> getLatestAccountingRegistries(@PathVariable("branchId") Integer id){
        return service.getLastAccountingRegistries(id);
    }

    @GetMapping("/by-page")
    public Page<AccountingDto> getgetLatestAccountingRegistriesByPage(
            @RequestParam Integer branchId,
            @PageableDefault(size = 12, sort = "date", direction = Sort.Direction.DESC) Pageable pageable
    ){
        return service.getLastAccountingRegistriesByPage(pageable, branchId);
    }

    @GetMapping("/account/{accountingId}")
    public AccountingDto getAccounting(@PathVariable("accountingId") Integer id){
        return service.getAccounting(id)
                .orElseThrow(() -> new ResourceNotFoundException("Accounting", id));
    }

    @GetMapping("/range")
    //jiltsa/api/v1/accounts/2023-03-25T18:29:08.608983/2023-03-29T18:29:08.608983/1
    public Page<AccountingDto> getAccountingRegistriesBetweenDates(
            @RequestParam LocalDateTime initial,
            @RequestParam LocalDateTime end,
            @RequestParam Integer branchId,
            @PageableDefault(size = 12, sort = "date") Pageable pageable
            ){
        return service.getAccountingRegistriesBetweenTwoDates(pageable, initial, end, branchId);
    }

    @PostMapping
    public CreateAccountingDto createAccounting(@Valid @RequestBody CreateAccountingDto createAccountingDto){
        return service.createAccounting(createAccountingDto);
    }

    @PostMapping("/out-of-date")
    public CustomAccountingDto createOutOfDateAccounting(@Valid @RequestBody CustomAccountingDto customAccountingDto){
        return service.createOutOfDateAccounting(customAccountingDto);
    }

    @DeleteMapping("/account/{accountingId}")
    public void deleteAccounting(@PathVariable("accountingId") Integer accountingId){
        service.deleteAccounting(accountingId);
    }
}
