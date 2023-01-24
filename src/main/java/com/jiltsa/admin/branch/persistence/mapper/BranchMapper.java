package com.jiltsa.admin.branch.persistence.mapper;

import com.jiltsa.admin.branch.domain.dto.BranchDto;
import com.jiltsa.admin.branch.persistence.entity.Branch;
import com.jiltsa.admin.seller.persistence.mapper.SellerMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SellerMapper.class})
public interface BranchMapper {

    BranchDto toBranchDto(Branch branch);
    List<BranchDto> toBranchesDto(List<Branch> branches);

    @InheritInverseConfiguration
    @Mapping(target = "branch", ignore = true)
    Branch toBranch(BranchDto branchDto);
    List<Branch> toBranches(List<BranchDto> branchesDto);
}
