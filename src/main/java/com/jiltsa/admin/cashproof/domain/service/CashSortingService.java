package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.CashSortingDto;
import com.jiltsa.admin.cashproof.domain.repository.CashSortingDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CashSortingService {
    private final CashSortingDRepository repository;

    public Optional<CashSortingDto> getCashSorting(Integer accountingDto){
        return repository.getCashSorting(accountingDto);
    }

    public CashSortingDto saveCashSorting(CashSortingDto cashSortingDto){
        return repository.saveCashSorting(cashSortingDto);
    }
}
