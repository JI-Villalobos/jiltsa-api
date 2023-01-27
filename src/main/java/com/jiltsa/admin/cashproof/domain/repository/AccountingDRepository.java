package com.jiltsa.admin.cashproof.domain.repository;

import com.jiltsa.admin.cashproof.domain.dto.AccountingDto;

import java.util.List;

public interface AccountingDRepository {
    List<AccountingDto> getLastAccountingRegistries();
    List<AccountingDto> getAccountingRegistriesBetweenTwoDates(String start, String end);
    AccountingDto createAccounting(AccountingDto accountingDto);
}
