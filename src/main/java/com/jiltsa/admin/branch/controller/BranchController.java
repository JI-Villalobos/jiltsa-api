package com.jiltsa.admin.branch.controller;

import com.jiltsa.admin.branch.domain.dto.BranchDto;
import com.jiltsa.admin.branch.domain.dto.TotalBalanceDto;
import com.jiltsa.admin.branch.domain.service.BranchDService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/branches")
@RequiredArgsConstructor
public class BranchController{
    private final BranchDService service;
    @GetMapping
    public List<BranchDto> getBranches(){
        return service.getAll();
    }
    @GetMapping("/{branchId}")
    public BranchDto getBranch(@PathVariable("branchId") Integer branchId){
        return service.getById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", branchId));
    }
    @PostMapping
    public BranchDto createBranch(@RequestBody BranchDto branchDto){
        return service.createBranch(branchDto);
    }

    @GetMapping("/balance/{branchId}")
    public TotalBalanceDto getTotalBalance(@PathVariable("branchId") Integer branchId){
        return service.getTotalBalance(branchId);
    }
}
