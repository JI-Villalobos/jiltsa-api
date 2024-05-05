package com.jiltsa.admin.cashproof.controller;

import com.jiltsa.admin.cashproof.domain.dto.CashWithdrawalDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateCashWithdrawalDto;
import com.jiltsa.admin.cashproof.domain.service.CashWithdrawalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public CreateCashWithdrawalDto createCashWithdrawalDto(@RequestBody CreateCashWithdrawalDto createCashWithdrawalDto){
        return service.createCashWithdrawal(createCashWithdrawalDto);
    }

    @GetMapping("/last-month/{branch}")
    public Page<CashWithdrawalDto> getLatestMonthRegistries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int elements,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "acs") String sortDirection,
            @PathVariable("branch") String branch){
        return service.getLatestMonthRegistries(page, elements, sortBy, sortDirection, branch);
    }

    @GetMapping("/current/{branch}")
    public List<CashWithdrawalDto> getCurrentRegistries(@PathVariable("branch") String branch){
        return service.getCurrentCashWithdrawalsRegistries(branch);
    }

    @GetMapping("/latest/{branch}/{date}/{tag}")
    public Page<CashWithdrawalDto> getRegistriesByTagAndDate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int elements,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "acs") String sortDirection,
            @PathVariable("branch") String branch,
            @PathVariable("date") LocalDateTime date,
            @PathVariable("tag") String concept){
        return service.getRegistriesByTagAndDate(page, elements, sortBy, sortDirection, branch, concept, date);
    }

    @GetMapping("/{branch}/since/{date}")
    public Page<CashWithdrawalDto> getRegistriesByDate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int elements,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "acs") String sortDirection,
            @PathVariable("branch") String branch,
            @PathVariable("date") LocalDateTime date){
        return service.getRegistriesByDate(page, elements, sortBy, sortDirection, branch, date);
    }
}
