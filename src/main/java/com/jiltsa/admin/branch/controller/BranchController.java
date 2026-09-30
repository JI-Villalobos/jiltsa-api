package com.jiltsa.admin.branch.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.branch.domain.dto.BranchDto;
import com.jiltsa.admin.branch.domain.dto.TotalBalanceDto;
import com.jiltsa.admin.branch.domain.service.BranchService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import com.jiltsa.admin.security.AdminOnly;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/branches")
@RequiredArgsConstructor
public class BranchController{
    private final BranchService service;
    @Operation(summary = "List all branches")
    @GetMapping
    public List<BranchDto> getBranches(){
        return service.getAll();
    }
    @Operation(summary = "Get a branch")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No branch with that id")
    @GetMapping("/{branchId}")
    public BranchDto getBranch(@Parameter(description = "Branch id") @PathVariable("branchId") Integer branchId){
        return service.getById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", branchId));
    }
    @Operation(summary = "Create a branch")
    @AdminOnly
    @PostMapping
    public BranchDto createBranch(@Valid @RequestBody BranchDto branchDto){
        return service.createBranch(branchDto);
    }

    @Operation(summary = "Get the cash balance of a branch")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No branch with that id")
    @GetMapping("/balance/{branchId}")
    public TotalBalanceDto getTotalBalance(@Parameter(description = "Branch id") @PathVariable("branchId") Integer branchId){
        return service.getTotalBalance(branchId);
    }
}
