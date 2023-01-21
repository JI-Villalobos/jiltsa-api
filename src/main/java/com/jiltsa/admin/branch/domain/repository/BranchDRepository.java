package com.jiltsa.admin.branch.domain.repository;

import com.jiltsa.admin.branch.domain.dto.BranchDto;
import java.util.List;

public interface BranchDRepository {
    List<BranchDto> getAll();
    BranchDto createBranch(BranchDto branchDto);
}
