package com.jiltsa.admin.branch.domain.service;

import com.jiltsa.admin.branch.domain.dto.BranchDto;
import com.jiltsa.admin.branch.domain.repository.BranchDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchDService {
    private final BranchDRepository branchDRepository;

    public List<BranchDto> getAll(){
        return branchDRepository.getAll();
    }

    public BranchDto createBranch(BranchDto branchDto){
        return branchDRepository.createBranch(branchDto);
    }
}
