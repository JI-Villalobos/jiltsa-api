package com.jiltsa.admin.operativity.domain.service;

import com.jiltsa.admin.operativity.domain.dto.AverageSalesResultDto;
import com.jiltsa.admin.operativity.domain.dto.SaleResultDto;
import com.jiltsa.admin.operativity.domain.repository.SaleResultDRepository;
import com.jiltsa.admin.operativity.persistence.repository.SaleResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleResultService {
    private final SaleResultDRepository repository;
    private final SaleResultRepository saleResultRepository;

    public List<SaleResultDto> findByBranch(Integer branchId){
        return repository.findByBranchId(branchId);
    }

    public List<SaleResultDto> findByBranchAndDateRange(Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate){
        return repository.findByBranchIdAndDateRange(branchId, initialDate, finalDate);
    }

    public SaleResultDto saveResult(SaleResultDto saleResultDto){
        return repository.saveResult(saleResultDto);
    }
}
