package com.jiltsa.admin.operativity.domain.repository;

import com.jiltsa.admin.operativity.domain.dto.SaleResultDto;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleResultDRepository {
    List<SaleResultDto> findByBranchId(Integer branchId);
    List<SaleResultDto> findByBranchIdAndDateRange(Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate);
    SaleResultDto saveResult(SaleResultDto saleResultDto);
}
