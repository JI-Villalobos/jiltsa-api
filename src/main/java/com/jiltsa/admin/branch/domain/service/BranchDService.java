package com.jiltsa.admin.branch.domain.service;

import com.jiltsa.admin.branch.domain.dto.BranchDto;
import com.jiltsa.admin.branch.domain.repository.BranchDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BranchDService {
    private final BranchDRepository branchDRepository;

    public List<BranchDto> getAll(){
        return branchDRepository.getAll();
    }
    public Optional<BranchDto> getById (Integer branchId){
        return  branchDRepository.getById(branchId);
    }
    public BranchDto createBranch(BranchDto branchDto){
        return branchDRepository.createBranch(branchDto);
    }
}
