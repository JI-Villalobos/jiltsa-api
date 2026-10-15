package com.jiltsa.admin.cashproof.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.cashproof.domain.dto.AccountingDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateAccountingDto;
import com.jiltsa.admin.cashproof.domain.dto.CustomAccountingDto;
import com.jiltsa.admin.cashproof.domain.service.AccountingService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import com.jiltsa.admin.security.AdminOnly;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    private final AccountingService service;

    @Operation(summary = "List accountings of all branches from the last four days")
    @GetMapping
    public Page<AccountingDto> getLatestAccountingRegistriesAllBranches(
            @PageableDefault(size = 12, sort = "date") Pageable pageable
    ){
        return service.getLastAccountingRegistriesAllBranches(pageable);
    }

    @Operation(summary = "List a branch's accountings from the last seven days", description = "Oldest first, with their incomes and expenses.")
    @GetMapping("/{branchId}")
    public List<AccountingDto> getLatestAccountingRegistries(@Parameter(description = "Branch id") @PathVariable("branchId") Integer id){
        return service.getLastAccountingRegistries(id);
    }

    @Operation(summary = "List a branch's accountings", description = "Newest first unless sortDirection says otherwise.")
    @GetMapping("/by-page")
    public Page<AccountingDto> getLatestAccountingRegistriesByPage(
            @Parameter(description = "Branch id") @RequestParam Integer branchId,
            @PageableDefault(size = 12, sort = "date", direction = Sort.Direction.DESC) Pageable pageable
    ){
        return service.getLastAccountingRegistriesByPage(pageable, branchId);
    }

    @Operation(summary = "Get an accounting")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No accounting with that id")
    @GetMapping("/account/{accountingId}")
    public AccountingDto getAccounting(@Parameter(description = "Accounting id") @PathVariable("accountingId") Integer id){
        return service.getAccounting(id)
                .orElseThrow(() -> new ResourceNotFoundException("Accounting", id));
    }

    @Operation(summary = "List a branch's accountings within a date range")
    @GetMapping("/range")
    //jiltsa/api/v1/accounts/2023-03-25T18:29:08.608983/2023-03-29T18:29:08.608983/1
    public Page<AccountingDto> getAccountingRegistriesBetweenDates(
            @Parameter(description = "Range start", example = "2026-03-01T00:00:00") @RequestParam LocalDateTime initial,
            @Parameter(description = "Range end", example = "2026-03-31T23:59:59") @RequestParam LocalDateTime end,
            @Parameter(description = "Branch id") @RequestParam Integer branchId,
            @PageableDefault(size = 12, sort = "date") Pageable pageable
            ){
        return service.getAccountingRegistriesBetweenTwoDates(pageable, initial, end, branchId);
    }

    @Operation(summary = "Open an accounting (cash proof) for a seller at a branch")
    @PostMapping
    public CreateAccountingDto createAccounting(@Valid @RequestBody CreateAccountingDto createAccountingDto){
        return service.createAccounting(createAccountingDto);
    }

    @Operation(summary = "Open an accounting with a past date", description = "For branches whose configuration allows out-of-date accountings.")
    @PostMapping("/out-of-date")
    public CustomAccountingDto createOutOfDateAccounting(@Valid @RequestBody CustomAccountingDto customAccountingDto){
        return service.createOutOfDateAccounting(customAccountingDto);
    }

    @Operation(summary = "Delete an accounting")
    @AdminOnly
    @DeleteMapping("/account/{accountingId}")
    public void deleteAccounting(@Parameter(description = "Accounting id") @PathVariable("accountingId") Integer accountingId){
        service.deleteAccounting(accountingId);
    }
}
