package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.AccountingDto;
import com.jiltsa.admin.cashproof.domain.repository.AccountingDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountingDService {
    private final AccountingDRepository accountingDRepository;

    public List<AccountingDto> getLastAccountingRegistries(){
        return accountingDRepository.getLastAccountingRegistries();
    }

    public List<AccountingDto> getAccountingRegistriesBetweenTwoDates(String start, String end){
        return accountingDRepository.getAccountingRegistriesBetweenTwoDates(start, end);
    }

    public AccountingDto createAccounting(AccountingDto accountingDto){
        return accountingDRepository.createAccounting(accountingDto);
    }
}
