package com.jiltsa.admin.branch.domain.service;

import com.jiltsa.admin.branch.domain.dto.BranchConfigurationDto;
import com.jiltsa.admin.branch.persistence.entity.BranchConfiguration;
import com.jiltsa.admin.branch.persistence.entity.Profile;
import com.jiltsa.admin.branch.persistence.mapper.BranchConfigurationMapper;
import com.jiltsa.admin.branch.persistence.repository.BranchConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BranchConfigurationService {
    private final BranchConfigurationRepository repository;
    private final BranchConfigurationMapper mapper;

    public List<BranchConfigurationDto> getAllConfiguration() {
        return mapper.toBranchConfigurationDtoList(repository.findAll());
    }

    @Transactional
    public BranchConfigurationDto createBranchConfiguration(BranchConfigurationDto branchConfigurationDto) {
        BranchConfiguration branchConfiguration = mapper.toBranchConfiguration(branchConfigurationDto);
        return mapper.toBranchConfigurationDto(repository.save(branchConfiguration));
    }

    @Transactional
    public BranchConfigurationDto updateBranchConfiguration(BranchConfigurationDto branchConfigurationDto) {
        BranchConfiguration branchConfiguration = mapper.toBranchConfiguration(branchConfigurationDto);
        return mapper.toBranchConfigurationDto(repository.save(branchConfiguration));
    }

    public Optional<BranchConfigurationDto> getBranchConfiguration(Integer branchId) {
        return repository.findByBranchId(branchId).map(mapper::toBranchConfigurationDto);
    }

    public List<BranchConfigurationDto> getBranchConfigurationsByProfile(Profile profile) {
        return mapper.toBranchConfigurationDtoList(repository.findByProfile(profile));
    }
}
