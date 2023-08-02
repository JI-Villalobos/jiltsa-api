package com.jiltsa.admin.branch.persistence.mapper;

import com.jiltsa.admin.branch.domain.dto.BranchConfigurationDto;
import com.jiltsa.admin.branch.persistence.entity.BranchConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BranchConfigurationMapper {
    BranchConfigurationDto toBranchConfigurationDto(BranchConfiguration branchConfiguration);
    List<BranchConfigurationDto> toBranchConfigurationDtoList(List<BranchConfiguration> branchConfigurationList);

    @InheritInverseConfiguration
    @Mapping(target = "modifiedDate", ignore = true)
    BranchConfiguration toBranchConfiguration(BranchConfigurationDto branchConfigurationDto);
}
