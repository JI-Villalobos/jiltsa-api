package com.jiltsa.admin.branch.persistence.repository;

import com.jiltsa.admin.branch.domain.dto.BranchDto;
import com.jiltsa.admin.branch.domain.repository.BranchDRepository;
import com.jiltsa.admin.branch.persistence.entity.Branch;
import com.jiltsa.admin.branch.persistence.mapper.BranchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BranchRepositoryImplementation implements BranchDRepository {
    private final BranchRepository repository;
    private final BranchMapper mapper;

    @Override
    public List<BranchDto> getAll() {
        return mapper.toBranchesDto(repository.findAll());
    }

    @Override
    public BranchDto createBranch(BranchDto branchDto) {
        Branch branch = mapper.toBranch(branchDto);
        return mapper.toBranchDto(repository.save(branch));
    }
}
