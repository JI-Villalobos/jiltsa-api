package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.domain.dto.AccountingDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateAccountingDto;
import com.jiltsa.admin.cashproof.domain.dto.CustomAccountingDto;
import com.jiltsa.admin.cashproof.domain.repository.AccountingDRepository;
import com.jiltsa.admin.cashproof.persistence.entity.Accounting;
import com.jiltsa.admin.cashproof.persistence.mapper.AccountingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<AccountingDto> getAccountingRegistriesBetweenTwoDates(
            int page, int elements, String sortBy, String sortDirection,
            LocalDateTime start, LocalDateTime end, Integer branchId
    ) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);
        return mapper.toAccountingDtoPage(repository.findByDateBetweenAndBranchIdOrderByDate(pageRequest, start, end, branchId));
    }

    @Override
    public List<AccountingDto> getLastAccountingRegistries(Integer branchId) {
        LocalDateTime date = LocalDateTime.now().minusDays(7);
        return mapper.toAccountingDtoList(repository.findByBranchIdAndDateAfterOrderByDateAsc(branchId, date));
    }

    @Override
    public List<AccountingDto> getLastAccountingRegistriesAllBranches() {
        LocalDateTime date = LocalDateTime.now().minusDays(4);
        return mapper.toAccountingDtoList(repository.findByDateAfterOrderByDateAsc(date));
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

    @Override
    public void deleteAccounting(Integer accountingId) {
        repository.deleteById(accountingId);
    }
}
