package com.jiltsa.admin.cashproof.domain.repository;

import com.jiltsa.admin.cashproof.domain.dto.CashSortingDto;
import com.jiltsa.admin.cashproof.persistence.entity.CashSorting;

import java.util.Optional;

public interface CashSortingDRepository {
    Optional<CashSortingDto> getCashSorting(Integer accountingId);
    CashSortingDto saveCashSorting(CashSorting cashSorting);
}
