package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.ActiveAccountingDto;
import com.jiltsa.admin.cashproof.domain.repository.ActiveAccountingDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ActiveAccountingService {
    private final ActiveAccountingDRepository repository;

    @Transactional
    public ActiveAccountingDto setActiveAccounting(ActiveAccountingDto activeAccountingDto){
        return repository.createActiveAccounting(activeAccountingDto);
    }

    public Optional<ActiveAccountingDto> getCurrentAccounting(Integer branchId){
        return repository.getCurrentAccounting(branchId);
    }

    @Transactional
    public Boolean closeCurrentAccounting(Integer accountingId){
        return repository.closeActiveAccounting(accountingId);
    }
}
