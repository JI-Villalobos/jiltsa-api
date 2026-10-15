package com.jiltsa.admin.operativity.domain.service;

import com.jiltsa.admin.operativity.domain.dto.SaleResultDto;
import com.jiltsa.admin.operativity.persistence.entity.SaleResult;
import com.jiltsa.admin.operativity.persistence.mapper.SaleResultMapper;
import com.jiltsa.admin.operativity.persistence.repository.SaleResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SaleResultService {
    private final SaleResultRepository repository;
    private final SaleResultMapper mapper;

    public List<SaleResultDto> findByBranch(Integer branchId) {
        LocalDateTime monthlyResults = LocalDateTime.now().minusMonths(1);
        return mapper.toSaleResultDtoList(repository.findByBranchIdAndInitialDateAfter(branchId, monthlyResults));
    }

    public List<SaleResultDto> findByBranchAndDateRange(Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate) {
        return mapper.toSaleResultDtoList(repository.findByBranchIdAndInitialDateBetween(branchId, initialDate, finalDate));
    }

    @Transactional
    public SaleResultDto saveResult(SaleResultDto saleResultDto) {
        SaleResult saleResult = mapper.toSaleResult(saleResultDto);

        return mapper.toSaleResultDto(repository.save(saleResult));
    }
}
