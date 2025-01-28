package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.AccountingDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateAccountingDto;
import com.jiltsa.admin.cashproof.domain.dto.CustomAccountingDto;
import com.jiltsa.admin.cashproof.domain.repository.AccountingDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountingDService {
    private final AccountingDRepository accountingDRepository;

    public List<AccountingDto> getLastAccountingRegistries(Integer branchId){
        return accountingDRepository.getLastAccountingRegistries(branchId);
    }

    public Page<AccountingDto> getLastAccountingRegistriesAllBranches(int page, int elements, String sortBy, String sortDirection){
        return accountingDRepository.getLastAccountingRegistriesAllBranches(page, elements, sortBy, sortDirection);
    }

    public Optional<AccountingDto> getAccounting(Integer accountingId){
        return accountingDRepository.getAccounting(accountingId);
    }

    public Page<AccountingDto> getAccountingRegistriesBetweenTwoDates(
            int page, int elements, String sortBy, String sortDirection,
            LocalDateTime start, LocalDateTime end, Integer branchId
            ){
        return accountingDRepository.getAccountingRegistriesBetweenTwoDates(page, elements, sortBy,
                sortDirection, start, end, branchId);
    }

    public Page<AccountingDto> getLastAccountingRegistriesByPage(int page, int elements, String sortBy, String sortDirection, Integer branchId){
        return accountingDRepository.getLastAccountingRegistriesByPage(page, elements, sortBy, sortDirection, branchId);
    }

    public CreateAccountingDto createAccounting(CreateAccountingDto createAccountingDto){
        return accountingDRepository.createAccounting(createAccountingDto);
    }

    public CustomAccountingDto createOutOfDateAccounting(CustomAccountingDto customAccountingDto){
        return accountingDRepository.createOutOfDateAccounting(customAccountingDto);
    }

    public void deleteAccounting(Integer accountingId){
        accountingDRepository.deleteAccounting(accountingId);
    }
}
