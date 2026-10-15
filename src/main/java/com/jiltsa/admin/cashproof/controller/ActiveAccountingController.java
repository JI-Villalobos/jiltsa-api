package com.jiltsa.admin.cashproof.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.cashproof.domain.dto.ActiveAccountingDto;
import com.jiltsa.admin.cashproof.domain.service.ActiveAccountingService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("jiltsa/api/v1/active-accounting")
@RequiredArgsConstructor
public class ActiveAccountingController {
    private final ActiveAccountingService service;

    @Operation(summary = "Mark an accounting as the branch's active one")
    @PostMapping
    public ActiveAccountingDto createActiveAccounting(@Valid @RequestBody ActiveAccountingDto activeAccountingDto){
        return service.setActiveAccounting(activeAccountingDto);
    }

    @Operation(summary = "Get the active accounting of a branch")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "The branch has no active accounting")
    @GetMapping("/branch/{branchId}")
    public ActiveAccountingDto getCurrentAccounting(@Parameter(description = "Branch id") @PathVariable("branchId") Integer branchId){
        return service.getCurrentAccounting(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("ActiveAccounting", branchId));
    }

    @Operation(summary = "Close an active accounting", description = "Returns false when the accounting was never marked active.")
    @PatchMapping("/{accountingId}")
    public Boolean closeActiveAccounting(@Parameter(description = "Accounting id") @PathVariable("accountingId") Integer accountingId){
        return service.closeCurrentAccounting(accountingId);
    }
}
