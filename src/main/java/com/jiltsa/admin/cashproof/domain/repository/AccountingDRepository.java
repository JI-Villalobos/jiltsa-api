package com.jiltsa.admin.cashproof.domain.repository;

import com.jiltsa.admin.cashproof.domain.dto.AccountingDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateAccountingDto;
import com.jiltsa.admin.cashproof.domain.dto.CustomAccountingDto;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AccountingDRepository {
    Optional<AccountingDto> getAccounting(Integer accountingId);
    Page<AccountingDto> getAccountingRegistriesBetweenTwoDates(int page, int elements, String sortBy, String sortDirection,
                                                               LocalDateTime start, LocalDateTime end, Integer branchId);
    List<AccountingDto> getLastAccountingRegistries(Integer branchId);
    List<AccountingDto> getLastAccountingRegistriesAllBranches();
    CreateAccountingDto createAccounting(CreateAccountingDto createAccountingDto);
    CustomAccountingDto createOutOfDateAccounting(CustomAccountingDto customAccountingDto);
}
