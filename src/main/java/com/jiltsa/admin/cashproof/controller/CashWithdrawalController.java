package com.jiltsa.admin.cashproof.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.cashproof.domain.dto.CashWithdrawalDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateCashWithdrawalDto;
import com.jiltsa.admin.cashproof.domain.service.CashWithdrawalService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import com.jiltsa.admin.security.AdminOnly;
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

    @GetMapping("/all/{branch}")
    public List<CashWithdrawalDto> getCashWithdrawals(@PathVariable("branch") String branch){
        return service.getCashWithdrawals(branch);
    }

    @PostMapping
    public CreateCashWithdrawalDto createCashWithdrawal(@Valid @RequestBody CreateCashWithdrawalDto createCashWithdrawalDto){
        return service.createCashWithdrawal(createCashWithdrawalDto);
    }

    @GetMapping("/last-month/{branch}")
    public Page<CashWithdrawalDto> getLatestMonthRegistries(
            @PageableDefault(size = 15, sort = "date") Pageable pageable,
            @PathVariable("branch") String branch){
        return service.getLatestMonthRegistries(pageable, branch);
    }

    @GetMapping("/current/{branch}")
    public List<CashWithdrawalDto> getCurrentRegistries(@PathVariable("branch") String branch){
        return service.getCurrentCashWithdrawalsRegistries(branch);
    }

    @GetMapping("/latest/{branch}/{start}/to/{finish}/{tag}")
    public Page<CashWithdrawalDto> getRegistriesByTagAndDate(
            @PageableDefault(size = 15, sort = "date") Pageable pageable,
            @PathVariable("branch") String branch,
            @PathVariable("start") LocalDateTime start,
            @PathVariable("finish") LocalDateTime finish,
            @PathVariable("tag") String concept){
        return service.getRegistriesByTagAndDate(pageable, branch, concept, start, finish);
    }

    @GetMapping("/{branch}/since/{start}/to/{finish}")
    public Page<CashWithdrawalDto> getRegistriesByDate(
            @PageableDefault(size = 15, sort = "date") Pageable pageable,
            @PathVariable("branch") String branch,
            @PathVariable("start") LocalDateTime start,
            @PathVariable("finish") LocalDateTime finish){
        return service.getRegistriesByDateBetween(pageable, branch, start, finish);
    }

    @GetMapping("/{cashId}")
    public CashWithdrawalDto getCashWithdrawal(@PathVariable("cashId") Integer cashId){
        return service.getCashWithdrawal(cashId)
                .orElseThrow(() -> new ResourceNotFoundException("CashWithdrawal", cashId));
    }

    @PutMapping
    public CashWithdrawalDto updateCashWithdrawal(@Valid @RequestBody CashWithdrawalDto cashWithdrawalDto){
        return service.updateCashWithdrawal(cashWithdrawalDto);
    }

    @AdminOnly
    @DeleteMapping("/{cashId}")
    public void deleteCashWithdrawal(@PathVariable("cashId") Integer cashId){
        service.deleteCashWithdrawal(cashId);
    }
}
