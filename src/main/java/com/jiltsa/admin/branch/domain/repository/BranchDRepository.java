package com.jiltsa.admin.branch.domain.repository;

import com.jiltsa.admin.branch.domain.dto.BranchDto;
import java.util.List;
import java.util.Optional;

public interface BranchDRepository {
    List<BranchDto> getAll();
    Optional<BranchDto> getById(Integer branchId);
    BranchDto createBranch(BranchDto branchDto);
}
