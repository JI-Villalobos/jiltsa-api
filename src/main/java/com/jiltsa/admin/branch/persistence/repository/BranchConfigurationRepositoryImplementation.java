package com.jiltsa.admin.branch.persistence.repository;

import com.jiltsa.admin.branch.domain.dto.BranchConfigurationDto;
import com.jiltsa.admin.branch.domain.repository.BranchConfigurationDRepository;
import com.jiltsa.admin.branch.persistence.entity.BranchConfiguration;
import com.jiltsa.admin.branch.persistence.mapper.BranchConfigurationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BranchConfigurationRepositoryImplementation implements BranchConfigurationDRepository {
    private final BranchConfigurationRepository repository;
    private final BranchConfigurationMapper mapper;
    @Override
    public List<BranchConfigurationDto> getAllConfigurations() {
        return mapper.toBranchConfigurationDtoList(repository.findAll());
    }

    @Override
    public BranchConfigurationDto createBranchConfiguration(BranchConfigurationDto branchConfigurationDto) {
        BranchConfiguration branchConfiguration = mapper.toBranchConfiguration(branchConfigurationDto);
        return mapper.toBranchConfigurationDto(repository.save(branchConfiguration));
    }

    @Override
    public BranchConfigurationDto updateBranchConfiguration(Integer branchConfigId, BranchConfigurationDto branchConfigurationDto) {
        BranchConfiguration branchConfiguration = mapper.toBranchConfiguration(branchConfigurationDto);
        return mapper.toBranchConfigurationDto(repository.save(branchConfiguration));
    }

    @Override
    public Optional<BranchConfigurationDto> getBranchconfiguration(Integer branchId) {
        return repository.findByBranchId(branchId).map(mapper::toBranchConfigurationDto);
    }
}
