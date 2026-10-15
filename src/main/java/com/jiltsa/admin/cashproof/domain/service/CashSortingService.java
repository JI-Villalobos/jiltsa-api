package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.CashSortingDto;
import com.jiltsa.admin.cashproof.persistence.entity.CashSorting;
import com.jiltsa.admin.cashproof.persistence.mapper.CashSortingMapper;
import com.jiltsa.admin.cashproof.persistence.repository.CashSortingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CashSortingService {
    private final CashSortingRepository repository;
    private final CashSortingMapper mapper;

    public Optional<CashSortingDto> getCashSorting(Integer accountingId) {
        return repository.findByAccountingId(accountingId).map(mapper::toCashSortingDto);
    }

    @Transactional
    public CashSortingDto saveCashSorting(CashSortingDto cashSortingDto) {
        CashSorting cashSorting = mapper.toCashSorting(cashSortingDto);

        return mapper.toCashSortingDto(repository.save(cashSorting));
    }
}
