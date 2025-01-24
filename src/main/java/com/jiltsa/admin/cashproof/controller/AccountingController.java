package com.jiltsa.admin.cashproof.controller;

import com.jiltsa.admin.cashproof.domain.dto.AccountingDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateAccountingDto;
import com.jiltsa.admin.cashproof.domain.dto.CustomAccountingDto;
import com.jiltsa.admin.cashproof.domain.service.AccountingDService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("jiltsa/api/v1/accounts")
@RequiredArgsConstructor
public class AccountingController {
    private final AccountingDService service;

    @GetMapping
    public List<AccountingDto> getLastesAccountingRegitriesAllBranches(){
        return service.getLastAccountingRegistriesAllBranches();
    }

    @GetMapping("/{branchId}")
    public List<AccountingDto> getLatestAccountingRegistries(@PathVariable("branchId") Integer id){
        return service.getLastAccountingRegistries(id);
    }

    @GetMapping("/account/{accountingId}")
    public Optional<AccountingDto> getAccounting(@PathVariable("accountingId") Integer id){
        return service.getAccounting(id);
    }

    @GetMapping("/range")
    //jiltsa/api/v1/accounts/2023-03-25T18:29:08.608983/2023-03-29T18:29:08.608983/1
    public Page<AccountingDto> getAccountingRegistriesBetweenDates(
            @RequestParam LocalDateTime initial,
            @RequestParam LocalDateTime end,
            @RequestParam Integer branchId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int elements,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "acs") String sortDirection
            ){
        return service.getAccountingRegistriesBetweenTwoDates(page, elements, sortBy, sortDirection, initial, end, branchId);
    }

    @PostMapping
    public CreateAccountingDto createAccounting(@RequestBody CreateAccountingDto createAccountingDto){
        return service.createAccounting(createAccountingDto);
    }

    @PostMapping("/out-of-date")
    public CustomAccountingDto createOutOfDateAccounting(@RequestBody CustomAccountingDto customAccountingDto){
        return service.createOutOfDateAccounting(customAccountingDto);
    }

    @DeleteMapping("/account/{accountingId}")
    public void deleteAccounting(@PathVariable("accountingId") Integer accountingId){
        service.deleteAccounting(accountingId);
    }
}
