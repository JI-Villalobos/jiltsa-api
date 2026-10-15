package com.jiltsa.admin.operativity.projection;

import com.jiltsa.admin.operativity.domain.dto.AverageSalesResultDto;
import com.jiltsa.admin.operativity.persistence.repository.SaleResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AverageSalesProjectionService {
    private final SaleResultRepository repository;

    public AverageSalesResultDto getAverageSales(Integer branchId){
        Double averageSales = repository.getAverageSales(branchId);

        return new AverageSalesResultDto(branchId, averageSales);
    }
}
