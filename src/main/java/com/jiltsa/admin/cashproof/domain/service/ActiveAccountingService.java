package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.ActiveAccountingDto;
import com.jiltsa.admin.cashproof.domain.repository.ActiveAccountingDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActiveAccountingService {
    private final ActiveAccountingDRepository repository;

    public ActiveAccountingDto setActiveAccounting(ActiveAccountingDto activeAccountingDto){
        return repository.createActiveAccounting(activeAccountingDto);
    }

    public Optional<ActiveAccountingDto> getCurrentAccounting(Integer branchId){
        return repository.getCurrentAccounting(branchId);
    }

    public Boolean closeCurrentAccounting(Integer accountingId){
        return repository.closeActiveAccounting(accountingId);
    }
}
