package com.jiltsa.admin.cashproof.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.cashproof.domain.dto.ActiveAccountingDto;
import com.jiltsa.admin.cashproof.domain.service.ActiveAccountingService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("jiltsa/api/v1/active-accounting")
@RequiredArgsConstructor
public class ActiveAccountingController {
    private final ActiveAccountingService service;

    @PostMapping
    public ActiveAccountingDto createActiveAccounting(@Valid @RequestBody ActiveAccountingDto activeAccountingDto){
        return service.setActiveAccounting(activeAccountingDto);
    }

    @GetMapping("/branch/{branchId}")
    public ActiveAccountingDto getCurrentAccounting(@PathVariable("branchId") Integer branchId){
        return service.getCurrentAccounting(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("ActiveAccounting", branchId));
    }

    @PatchMapping("/{accountingId}")
    public Boolean closeActiveAccounting(@PathVariable("accountingId") Integer accountingId){
        return service.closeCurrentAccounting(accountingId);
    }
}
