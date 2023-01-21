package com.jiltsa.admin.branch.controller;

import com.jiltsa.admin.branch.domain.dto.BranchDto;
import com.jiltsa.admin.branch.domain.service.BranchDService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branch")
@RequiredArgsConstructor
public class BranchController{
    private final BranchDService service;


    @GetMapping
    public List<BranchDto> getBranches(){
        return service.getAll();
    }

    @PostMapping
    public BranchDto createBranch(@RequestBody BranchDto branchDto){
        return service.createBranch(branchDto);
    }
}
