package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.AccountingDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateAccountingDto;
import com.jiltsa.admin.cashproof.domain.repository.AccountingDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountingDService {
    private final AccountingDRepository accountingDRepository;

    public List<AccountingDto> getLastAccountingRegistries(Integer branchId){
        return accountingDRepository.getLastAccountingRegistries(branchId);
    }

    public List<AccountingDto> getAccountingRegistriesBetweenTwoDates(
            LocalDateTime start, LocalDateTime end, Integer branchId
            ){
        return accountingDRepository.getAccountingRegistriesBetweenTwoDates(start, end, branchId);
    }

    public CreateAccountingDto createAccounting(CreateAccountingDto createAccountingDto){
        return accountingDRepository.createAccounting(createAccountingDto);
    }
}
