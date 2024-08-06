package com.jiltsa.admin.cashproof.controller;

import com.jiltsa.admin.cashproof.domain.dto.ActiveAccountingDto;
import com.jiltsa.admin.cashproof.domain.service.ActiveAccountingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("jiltsa/api/v1/active-accounting")
@RequiredArgsConstructor
public class ActiveAccountingController {
    private final ActiveAccountingService service;

    @PostMapping
    public ActiveAccountingDto createActiveAccounting(@RequestBody ActiveAccountingDto activeAccountingDto){
        return service.setActiveAccounting(activeAccountingDto);
    }

    @GetMapping("/branch/{branchId}")
    public Optional<ActiveAccountingDto> getCurrentAccounting(@PathVariable("branchId") Integer branchId){
        return service.getCurrentAccounting(branchId);
    }

    @PatchMapping("/{accountingId}")
    public void closeActiveAccounting(@PathVariable("accountingId") Integer accountingId){
        service.closeCurrentAccounting(accountingId);
    }
}
