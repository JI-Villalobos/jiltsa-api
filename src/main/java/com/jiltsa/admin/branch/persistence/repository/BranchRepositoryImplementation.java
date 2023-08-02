package com.jiltsa.admin.branch.persistence.repository;

import com.jiltsa.admin.branch.domain.dto.BranchDto;
import com.jiltsa.admin.branch.domain.dto.TotalBalanceDto;
import com.jiltsa.admin.branch.domain.repository.BranchDRepository;
import com.jiltsa.admin.branch.persistence.entity.Branch;
import com.jiltsa.admin.branch.persistence.mapper.BranchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    public Optional<BranchDto> getById(Integer branchId) {
        return repository.findById(branchId).map(mapper::toBranchDto);
    }

    @Override
    public BranchDto createBranch(BranchDto branchDto) {
        Branch branch = mapper.toBranch(branchDto);
        return mapper.toBranchDto(repository.save(branch));
    }

    @Override
    public TotalBalanceDto getTotalBalance(Integer branchId) {
        String branch = repository.findById(branchId).get().getName();
        Double total = repository.getTotalBalance(branchId, branch);

        return  new TotalBalanceDto(total);
    }
}
