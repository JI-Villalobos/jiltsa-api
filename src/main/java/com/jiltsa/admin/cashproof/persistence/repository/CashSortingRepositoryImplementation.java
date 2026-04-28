package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.domain.dto.CashSortingDto;
import com.jiltsa.admin.cashproof.domain.repository.CashSortingDRepository;
import com.jiltsa.admin.cashproof.persistence.entity.CashSorting;
import com.jiltsa.admin.cashproof.persistence.mapper.CashSortingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CashSortingRepositoryImplementation implements CashSortingDRepository {
    private final CashSortingRepository repository;
    private final CashSortingMapper mapper;

    @Override
    public Optional<CashSortingDto> getCashSorting(Integer accountingId) {
        return repository.findByAccountingId(accountingId).map(mapper::toCashSortingDto);
    }

    @Override
    public CashSortingDto saveCashSorting(CashSortingDto cashSortingDto) {
        CashSorting cashSorting = mapper.toCashSorting(cashSortingDto);

        return mapper.toCashSortingDto(repository.save(cashSorting));
    }
}
