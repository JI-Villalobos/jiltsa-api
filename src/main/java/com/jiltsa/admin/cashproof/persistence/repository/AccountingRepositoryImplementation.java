package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.domain.dto.AccountingDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateAccountingDto;
import com.jiltsa.admin.cashproof.domain.dto.CustomAccountingDto;
import com.jiltsa.admin.cashproof.domain.repository.AccountingDRepository;
import com.jiltsa.admin.cashproof.persistence.entity.Accounting;
import com.jiltsa.admin.cashproof.persistence.mapper.AccountingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountingRepositoryImplementation implements AccountingDRepository {
    private final AccountingRepository repository;
    private final AccountingMapper mapper;

    @Override
    public Optional<AccountingDto> getAccounting(Integer accountingId) {
        return repository.findById(accountingId).map(mapper::toAccountingDto);
    }

    @Override
    public List<AccountingDto> getAccountingRegistriesBetweenTwoDates(
            LocalDateTime start, LocalDateTime end, Integer branchId
    ) {
        return mapper.toAccountingDtoList(repository.findByDateBetweenAndBranchIdOrderByDateAsc(start, end, branchId));
    }

    @Override
    public List<AccountingDto> getLastAccountingRegistries(Integer branchId) {
        LocalDateTime date = LocalDateTime.now().minusDays(7);
        return mapper.toAccountingDtoList(repository.findByBranchIdAndDateAfterOrderByDateAsc(branchId, date));
    }

    @Override
    public CreateAccountingDto createAccounting(CreateAccountingDto createAccountingDto) {
        Accounting accounting = mapper.toAccounting(createAccountingDto);
        return mapper.toCreateAccountingDto(repository.save(accounting));
    }

    @Override
    public CustomAccountingDto createOutOfDateAccounting(CustomAccountingDto customAccountingDto) {
        Accounting accounting = mapper.toAccounting(customAccountingDto);
        return mapper.toCustomAccountingDto(repository.save(accounting));
    }
}
