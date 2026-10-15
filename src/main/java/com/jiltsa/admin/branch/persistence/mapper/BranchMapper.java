package com.jiltsa.admin.branch.persistence.mapper;

import com.jiltsa.admin.branch.domain.dto.BranchDto;
import com.jiltsa.admin.branch.persistence.entity.Branch;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BranchMapper {
    BranchDto toBranchDto(Branch branch);

    List<BranchDto> toBranchDtoList(List<Branch> branches);

    @InheritInverseConfiguration
    Branch toBranch(BranchDto branchDto);
    List<Branch> toBranchList(List<BranchDto> branchesDto);
}
