package com.jiltsa.admin.branch.domain.repository;

import com.jiltsa.admin.branch.domain.dto.BranchConfigurationDto;

import java.util.List;
import java.util.Optional;

public interface BranchConfigurationDRepository {
    List<BranchConfigurationDto> getAllConfigurations();
    BranchConfigurationDto createBranchConfiguration(BranchConfigurationDto branchConfigurationDto);
    BranchConfigurationDto updateBranchConfiguration(Integer branchConfigId, BranchConfigurationDto branchConfigurationDto);
    Optional<BranchConfigurationDto> getBranchConfiguration(Integer branchId);
}
