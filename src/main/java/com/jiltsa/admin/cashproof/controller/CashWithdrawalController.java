package com.jiltsa.admin.cashproof.controller;

import com.jiltsa.admin.cashproof.domain.dto.CashWithdrawalDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateCashWithdrawalDto;
import com.jiltsa.admin.cashproof.domain.service.CashWithdrawalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/withdrawals")
@RequiredArgsConstructor
public class CashWithdrawalController {
    private final CashWithdrawalService service;

    @GetMapping("/{branch}")
    public List<CashWithdrawalDto> getCashWithdrawals(@PathVariable("branch") String branch){
        return service.getCashWithdrawals(branch);
    }

    @PostMapping
    public CreateCashWithdrawalDto createCashWithdrawalDto(@RequestBody CreateCashWithdrawalDto createCashWithdrawalDto){
        return service.createCashWithdrawal(createCashWithdrawalDto);
    }

}
