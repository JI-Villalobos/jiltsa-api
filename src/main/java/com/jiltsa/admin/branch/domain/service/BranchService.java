package com.jiltsa.admin.branch.domain.service;

import com.jiltsa.admin.branch.domain.dto.BranchDto;
import com.jiltsa.admin.branch.domain.dto.TotalBalanceDto;
import com.jiltsa.admin.branch.persistence.entity.Branch;
import com.jiltsa.admin.branch.persistence.mapper.BranchMapper;
import com.jiltsa.admin.branch.persistence.repository.BranchRepository;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
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
        if (!repository.existsById(branchId)) {
            throw new ResourceNotFoundException("Branch", branchId);
        }
        return new TotalBalanceDto(repository.getTotalBalance(branchId));
    }
}
