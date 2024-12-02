package com.jiltsa.admin.branch.controller;

import com.jiltsa.admin.branch.domain.dto.BranchConfigurationDto;
import com.jiltsa.admin.branch.domain.service.BranchConfigurationService;
import com.jiltsa.admin.branch.persistence.entity.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("jiltsa/api/v1/branch-configuration")
@RequiredArgsConstructor
public class BranchConfigurationController {
    private final BranchConfigurationService service;

    @GetMapping
    public List<BranchConfigurationDto> getConfigurations(){
        return service.getAllConfiguration();
    }

    @PostMapping
    public BranchConfigurationDto createBranchConfiguration(@RequestBody BranchConfigurationDto branchConfigurationDto){
        return service.createBranchConfiguration(branchConfigurationDto);
    }

    @PutMapping()
    public BranchConfigurationDto updateBranchConfiguration(@RequestBody BranchConfigurationDto branchConfigurationDto){
        return service.updateBranchConfiguration(branchConfigurationDto);
    }

    @GetMapping("/get/{branchId}")
    public Optional<BranchConfigurationDto> getBranchConfiguration(@PathVariable("branchId") Integer branchId){
        return service.getBranchConfiguration(branchId);
    }

    @GetMapping("/profile/{profile}")
    public List<BranchConfigurationDto> getBranchconfigurationsByProfile(@PathVariable("profile")Profile profile){
        return service.getBranchConfigurationsByProfile(profile);
    }
}
