package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.domain.dto.ActiveAccountingDto;
import com.jiltsa.admin.cashproof.domain.repository.ActiveAccountingDRepository;
import com.jiltsa.admin.cashproof.persistence.entity.ActiveAccounting;
import com.jiltsa.admin.cashproof.persistence.mapper.ActiveAccountingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ActiveAccountingRepositoryImplementation implements ActiveAccountingDRepository {
    private final ActiveAccountingRepository repository;
    private  final ActiveAccountingMapper mapper;

    @Override
    public Optional<ActiveAccountingDto> getCurrentAccounting(Integer branchId) {
        return repository.findByBranchIdAndIsActiveTrue(branchId).map(mapper::toActiveAccountingDto);
    }

    @Override
    public ActiveAccountingDto createActiveAccounting(ActiveAccountingDto accountingDto) {
        ActiveAccounting activeAccounting = mapper.toActiveAccounting(accountingDto);
        return mapper.toActiveAccountingDto(repository.save(activeAccounting));
    }

    @Override
    public void closeActiveAccounting(Integer accountingId) {
        Optional<ActiveAccounting> activeAccounting = repository.findByAccountingId(accountingId);
        if (activeAccounting.isPresent()) {
            ActiveAccounting accounting = activeAccounting.get();
            accounting.setIsActive(false);
            repository.save(accounting);
        } else {
            throw new RuntimeException("Active accounting not found");
        }
    }
}
