package com.jiltsa.admin.branch.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.branch.domain.dto.BranchDto;
import com.jiltsa.admin.branch.domain.dto.TotalBalanceDto;
import com.jiltsa.admin.branch.domain.service.BranchService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import com.jiltsa.admin.security.AdminOnly;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/branches")
@RequiredArgsConstructor
public class BranchController{
    private final BranchService service;
    @GetMapping
    public List<BranchDto> getBranches(){
        return service.getAll();
    }
    @GetMapping("/{branchId}")
    public BranchDto getBranch(@PathVariable("branchId") Integer branchId){
        return service.getById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", branchId));
    }
    @AdminOnly
    @PostMapping
    public BranchDto createBranch(@Valid @RequestBody BranchDto branchDto){
        return service.createBranch(branchDto);
    }

    @GetMapping("/balance/{branchId}")
    public TotalBalanceDto getTotalBalance(@PathVariable("branchId") Integer branchId){
        return service.getTotalBalance(branchId);
    }
}
