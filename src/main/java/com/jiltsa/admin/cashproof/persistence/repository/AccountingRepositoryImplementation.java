package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.domain.dto.AccountingDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateAccountingDto;
import com.jiltsa.admin.cashproof.domain.repository.AccountingDRepository;
import com.jiltsa.admin.cashproof.persistence.entity.Accounting;
import com.jiltsa.admin.cashproof.persistence.mapper.AccountingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccountingRepositoryImplementation implements AccountingDRepository {
    private final AccountingRepository repository;
    private final AccountingMapper mapper;
    @Override
    public List<AccountingDto> getLastAccountingRegistries(Integer branchId) {
        return mapper.toAccountingDtoList(repository.findTop20ByBranchIdOrderByIdDesc(branchId));
    }

    @Override
    public List<AccountingDto> getAccountingRegistriesBetweenTwoDates(
            LocalDateTime start, LocalDateTime end, Integer branchId
    ) {
        return mapper.toAccountingDtoList(repository.findByDateBetweenAndBranchId(start, end, branchId));
    }

    @Override
    public CreateAccountingDto createAccounting(CreateAccountingDto createAccountingDto) {
        Accounting accounting = mapper.toAccounting(createAccountingDto);
        return mapper.toCreateAccountingDto(repository.save(accounting));
    }
}
