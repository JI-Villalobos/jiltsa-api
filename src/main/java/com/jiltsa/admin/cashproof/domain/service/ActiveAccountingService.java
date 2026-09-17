package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.ActiveAccountingDto;
import com.jiltsa.admin.cashproof.persistence.entity.ActiveAccounting;
import com.jiltsa.admin.cashproof.persistence.mapper.ActiveAccountingMapper;
import com.jiltsa.admin.cashproof.persistence.repository.ActiveAccountingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ActiveAccountingService {
    private final ActiveAccountingRepository repository;
    private final ActiveAccountingMapper mapper;

    @Transactional
    public ActiveAccountingDto setActiveAccounting(ActiveAccountingDto accountingDto) {
        ActiveAccounting activeAccounting = mapper.toActiveAccounting(accountingDto);
        return mapper.toActiveAccountingDto(repository.save(activeAccounting));
    }

    public Optional<ActiveAccountingDto> getCurrentAccounting(Integer branchId) {
        return repository.findByBranchIdAndIsActiveTrue(branchId).map(mapper::toActiveAccountingDto);
    }

    @Transactional
    public Boolean closeCurrentAccounting(Integer accountingId) {
        Optional<ActiveAccounting> activeAccounting = repository.findByAccountingId(accountingId);
        if (activeAccounting.isPresent()) {
            ActiveAccounting accounting = activeAccounting.get();
            accounting.setIsActive(false);
            repository.save(accounting);

            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
