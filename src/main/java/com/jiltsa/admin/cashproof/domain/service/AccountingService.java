package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.AccountingDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateAccountingDto;
import com.jiltsa.admin.cashproof.domain.dto.CustomAccountingDto;
import com.jiltsa.admin.cashproof.persistence.entity.Accounting;
import com.jiltsa.admin.cashproof.persistence.mapper.AccountingMapper;
import com.jiltsa.admin.cashproof.persistence.repository.AccountingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountingService {
    private final AccountingRepository repository;
    private final AccountingMapper mapper;

    public List<AccountingDto> getLastAccountingRegistries(Integer branchId) {
        LocalDateTime date = LocalDateTime.now().minusDays(7);
        return mapper.toAccountingDtoList(repository.findByBranchIdAndDateAfterOrderByDateAsc(branchId, date));
    }

    public Page<AccountingDto> getLastAccountingRegistriesAllBranches(Pageable pageable) {
        LocalDateTime date = LocalDateTime.now().minusDays(4);
        return mapper.toAccountingDtoPage(repository.findByDateAfter(pageable, date));
    }

    public Optional<AccountingDto> getAccounting(Integer accountingId) {
        return repository.findById(accountingId).map(mapper::toAccountingDto);
    }

    public Page<AccountingDto> getAccountingRegistriesBetweenTwoDates(
            Pageable pageable, LocalDateTime start, LocalDateTime end, Integer branchId
    ) {
        return mapper.toAccountingDtoPage(repository.findByDateBetweenAndBranchId(pageable, start, end, branchId));
    }

    public Page<AccountingDto> getLastAccountingRegistriesByPage(Pageable pageable, Integer branchId) {
        LocalDateTime date = LocalDateTime.now().minusDays(4);
        return mapper.toAccountingDtoPage(repository.findByBranchIdAndDateAfter(pageable, branchId, date));
    }

    @Transactional
    public CreateAccountingDto createAccounting(CreateAccountingDto createAccountingDto) {
        Accounting accounting = mapper.toAccounting(createAccountingDto);
        return mapper.toCreateAccountingDto(repository.save(accounting));
    }

    @Transactional
    public CustomAccountingDto createOutOfDateAccounting(CustomAccountingDto customAccountingDto) {
        Accounting accounting = mapper.toAccounting(customAccountingDto);
        return mapper.toCustomAccountingDto(repository.save(accounting));
    }

    @Transactional
    public void deleteAccounting(Integer accountingId) {
        repository.deleteById(accountingId);
    }
}
