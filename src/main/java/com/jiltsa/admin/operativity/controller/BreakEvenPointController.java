package com.jiltsa.admin.operativity.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.operativity.domain.dto.BreakEvenPointDto;
import com.jiltsa.admin.operativity.domain.service.BreakEvenPointService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("jiltsa/api/v1/bep")
@RequiredArgsConstructor
public class BreakEvenPointController {
    private final BreakEvenPointService service;

    @Operation(summary = "Save the break-even point of a branch", description = "Monthly fixed costs: payroll, rent, services and so on.")
    @PostMapping
    public BreakEvenPointDto saveBEP(@Valid @RequestBody BreakEvenPointDto breakEvenPointDto){
        return service.saveBEP(breakEvenPointDto);
    }

    @Operation(summary = "Update the break-even point of a branch")
    @PutMapping
    public BreakEvenPointDto updateBEP(@Valid @RequestBody BreakEvenPointDto breakEvenPointDto){
        return service.saveBEP(breakEvenPointDto);
    }

    @Operation(summary = "Get the break-even point of a branch")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "The branch has no break-even point")
    @GetMapping("/branch/{branchId}")
    public BreakEvenPointDto getBEP(@Parameter(description = "Branch id") @PathVariable("branchId") Integer branchId){
        return service.getBreakEvenPoint(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("BreakEvenPoint", branchId));
    }
}
