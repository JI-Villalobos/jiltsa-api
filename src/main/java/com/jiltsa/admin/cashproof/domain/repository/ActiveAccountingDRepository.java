package com.jiltsa.admin.cashproof.domain.repository;

import com.jiltsa.admin.cashproof.domain.dto.ActiveAccountingDto;

import java.util.Optional;

public interface ActiveAccountingDRepository {
    Optional<ActiveAccountingDto> getCurrentAccounting(Integer branchId);
    ActiveAccountingDto createActiveAccounting(ActiveAccountingDto accountingDto);
    Boolean closeActiveAccounting(Integer accountingId);
}
