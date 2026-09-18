package com.jiltsa.admin.branch.domain.service;

import com.jiltsa.admin.branch.domain.dto.BranchDto;
import com.jiltsa.admin.branch.domain.dto.TotalBalanceDto;
import com.jiltsa.admin.branch.persistence.entity.Branch;
import com.jiltsa.admin.branch.persistence.mapper.BranchMapper;
import com.jiltsa.admin.branch.persistence.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BranchService {
    private final BranchRepository repository;
    private final BranchMapper mapper;

    public List<BranchDto> getAll() {
        return mapper.toBranchDtoList(repository.findAll());
    }

    public Optional<BranchDto> getById(Integer branchId) {
        return repository.findById(branchId).map(mapper::toBranchDto);
    }

    @Transactional
    public BranchDto createBranch(BranchDto branchDto) {
        Branch branch = mapper.toBranch(branchDto);
        return mapper.toBranchDto(repository.save(branch));
    }

    public TotalBalanceDto getTotalBalance(Integer branchId) {
        String branch = repository.findById(branchId).get().getName();
        Double total = repository.getTotalBalance(branchId, branch);

        return  new TotalBalanceDto(total);
    }
}
