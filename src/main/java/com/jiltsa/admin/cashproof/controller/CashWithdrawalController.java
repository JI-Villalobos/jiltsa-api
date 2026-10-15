package com.jiltsa.admin.cashproof.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.cashproof.domain.dto.CashWithdrawalDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateCashWithdrawalDto;
import com.jiltsa.admin.cashproof.domain.service.CashWithdrawalService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import com.jiltsa.admin.security.AdminOnly;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/withdrawals")
@RequiredArgsConstructor
public class CashWithdrawalController {
    private final CashWithdrawalService service;

    @Operation(summary = "List all cash withdrawals of a branch")
    @GetMapping("/all/{branchId}")
    public List<CashWithdrawalDto> getCashWithdrawals(@Parameter(description = "Branch id") @PathVariable("branchId") Integer branchId){
        return service.getCashWithdrawals(branchId);
    }

    @Operation(summary = "Register a cash withdrawal", description = "The date is set by the server.")
    @PostMapping
    public CreateCashWithdrawalDto createCashWithdrawal(@Valid @RequestBody CreateCashWithdrawalDto createCashWithdrawalDto){
        return service.createCashWithdrawal(createCashWithdrawalDto);
    }

    @Operation(summary = "List a branch's cash withdrawals from the last month")
    @GetMapping("/last-month/{branchId}")
    public Page<CashWithdrawalDto> getLatestMonthRegistries(
            @PageableDefault(size = 15, sort = "date") Pageable pageable,
            @Parameter(description = "Branch id") @PathVariable("branchId") Integer branchId){
        return service.getLatestMonthRegistries(pageable, branchId);
    }

    @Operation(summary = "List a branch's cash withdrawals from the last 24 hours")
    @GetMapping("/current/{branchId}")
    public List<CashWithdrawalDto> getCurrentRegistries(@Parameter(description = "Branch id") @PathVariable("branchId") Integer branchId){
        return service.getCurrentCashWithdrawalsRegistries(branchId);
    }

    @Operation(summary = "List a branch's cash withdrawals by concept within a date range", description = "Matches withdrawals whose concept contains the tag.")
    @GetMapping("/latest/{branchId}/{start}/to/{finish}/{tag}")
    public Page<CashWithdrawalDto> getRegistriesByTagAndDate(
            @PageableDefault(size = 15, sort = "date") Pageable pageable,
            @Parameter(description = "Branch id") @PathVariable("branchId") Integer branchId,
            @Parameter(description = "Range start", example = "2026-03-01T00:00:00") @PathVariable("start") LocalDateTime start,
            @Parameter(description = "Range end", example = "2026-03-31T23:59:59") @PathVariable("finish") LocalDateTime finish,
            @Parameter(description = "Text the concept must contain") @PathVariable("tag") String concept){
        return service.getRegistriesByTagAndDate(pageable, branchId, concept, start, finish);
    }

    @Operation(summary = "List a branch's cash withdrawals within a date range")
    @GetMapping("/{branchId}/since/{start}/to/{finish}")
    public Page<CashWithdrawalDto> getRegistriesByDate(
            @PageableDefault(size = 15, sort = "date") Pageable pageable,
            @Parameter(description = "Branch id") @PathVariable("branchId") Integer branchId,
            @Parameter(description = "Range start", example = "2026-03-01T00:00:00") @PathVariable("start") LocalDateTime start,
            @Parameter(description = "Range end", example = "2026-03-31T23:59:59") @PathVariable("finish") LocalDateTime finish){
        return service.getRegistriesByDateBetween(pageable, branchId, start, finish);
    }

    @Operation(summary = "Get a cash withdrawal")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No cash withdrawal with that id")
    @GetMapping("/{cashId}")
    public CashWithdrawalDto getCashWithdrawal(@Parameter(description = "Cash withdrawal id") @PathVariable("cashId") Integer cashId){
        return service.getCashWithdrawal(cashId)
                .orElseThrow(() -> new ResourceNotFoundException("CashWithdrawal", cashId));
    }

    @Operation(summary = "Update a cash withdrawal")
    @PutMapping
    public CashWithdrawalDto updateCashWithdrawal(@Valid @RequestBody CashWithdrawalDto cashWithdrawalDto){
        return service.updateCashWithdrawal(cashWithdrawalDto);
    }

    @Operation(summary = "Delete a cash withdrawal")
    @AdminOnly
    @DeleteMapping("/{cashId}")
    public void deleteCashWithdrawal(@Parameter(description = "Cash withdrawal id") @PathVariable("cashId") Integer cashId){
        service.deleteCashWithdrawal(cashId);
    }
}
