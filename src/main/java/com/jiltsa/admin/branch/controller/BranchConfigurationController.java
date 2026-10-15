package com.jiltsa.admin.branch.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.branch.domain.dto.BranchConfigurationDto;
import com.jiltsa.admin.branch.domain.service.BranchConfigurationService;
import com.jiltsa.admin.branch.domain.Profile;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import com.jiltsa.admin.security.AdminOnly;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/branch-configuration")
@RequiredArgsConstructor
public class BranchConfigurationController {
    private final BranchConfigurationService service;

    @Operation(summary = "List the configuration of every branch")
    @GetMapping
    public List<BranchConfigurationDto> getConfigurations(){
        return service.getAllConfiguration();
    }

    @Operation(summary = "Create a branch configuration")
    @AdminOnly
    @PostMapping
    public BranchConfigurationDto createBranchConfiguration(@Valid @RequestBody BranchConfigurationDto branchConfigurationDto){
        return service.createBranchConfiguration(branchConfigurationDto);
    }

    @Operation(summary = "Update a branch configuration")
    @AdminOnly
    @PutMapping()
    public BranchConfigurationDto updateBranchConfiguration(@Valid @RequestBody BranchConfigurationDto branchConfigurationDto){
        return service.updateBranchConfiguration(branchConfigurationDto);
    }

    @Operation(summary = "Get the configuration of a branch")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "The branch has no configuration")
    @GetMapping("/get/{branchId}")
    public BranchConfigurationDto getBranchConfiguration(@Parameter(description = "Branch id") @PathVariable("branchId") Integer branchId){
        return service.getBranchConfiguration(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("BranchConfiguration", branchId));
    }

    @Operation(summary = "List the configurations of branches with a profile")
    @GetMapping("/profile/{profile}")
    public List<BranchConfigurationDto> getBranchconfigurationsByProfile(@Parameter(description = "Branch profile") @PathVariable("profile")Profile profile){
        return service.getBranchConfigurationsByProfile(profile);
    }
}
