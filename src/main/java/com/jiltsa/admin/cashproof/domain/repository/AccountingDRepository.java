package com.jiltsa.admin.cashproof.domain.repository;

import com.jiltsa.admin.cashproof.domain.dto.AccountingDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateAccountingDto;

import java.time.LocalDateTime;
import java.util.List;

public interface AccountingDRepository {
    List<AccountingDto> getLastAccountingRegistries();
    List<AccountingDto> getAccountingRegistriesBetweenTwoDates(LocalDateTime start, LocalDateTime end);
    CreateAccountingDto createAccounting(CreateAccountingDto createAccountingDto);
}
