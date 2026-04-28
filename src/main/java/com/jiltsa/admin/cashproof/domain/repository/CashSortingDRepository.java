package com.jiltsa.admin.cashproof.domain.repository;

import com.jiltsa.admin.cashproof.domain.dto.CashSortingDto;

import java.util.Optional;

public interface CashSortingDRepository {
    Optional<CashSortingDto> getCashSorting(Integer accountingId);
    CashSortingDto saveCashSorting(CashSortingDto cashSorting);
}
