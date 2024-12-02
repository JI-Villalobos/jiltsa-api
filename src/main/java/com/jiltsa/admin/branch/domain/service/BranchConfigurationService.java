package com.jiltsa.admin.branch.domain.service;

import com.jiltsa.admin.branch.domain.dto.BranchConfigurationDto;
import com.jiltsa.admin.branch.domain.repository.BranchConfigurationDRepository;
import com.jiltsa.admin.branch.persistence.entity.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BranchConfigurationService {
    private final BranchConfigurationDRepository branchConfigurationDRepository;

    public List<BranchConfigurationDto> getAllConfiguration(){
        return branchConfigurationDRepository.getAllConfigurations();
    }

    public BranchConfigurationDto createBranchConfiguration(BranchConfigurationDto branchConfigurationDto){
        return branchConfigurationDRepository.createBranchConfiguration(branchConfigurationDto);
    }

    public BranchConfigurationDto updateBranchConfiguration(BranchConfigurationDto branchConfigurationDto){
        return branchConfigurationDRepository.updateBranchConfiguration(branchConfigurationDto);
    }

    public Optional<BranchConfigurationDto> getBranchConfiguration(Integer branchId){
        return branchConfigurationDRepository.getBranchConfiguration(branchId);
    }

    public List<BranchConfigurationDto> getBranchConfigurationsByProfile(Profile profile){
        return branchConfigurationDRepository.getConfigurationsByProfile(profile);
    }
}
