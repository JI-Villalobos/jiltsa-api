package com.jiltsa.admin.operativity.persistence.repository;

import com.jiltsa.admin.operativity.domain.dto.SaleResultDto;
import com.jiltsa.admin.operativity.domain.repository.SaleResultDRepository;
import com.jiltsa.admin.operativity.persistence.entity.SaleResult;
import com.jiltsa.admin.operativity.persistence.mapper.SaleResultMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SaleResultRepositoryImplementation implements SaleResultDRepository {
    private final SaleResultRepository repository;
    private final SaleResultMapper mapper;

    @Override
    public List<SaleResultDto> findByBranchId(Integer branchId) {
        LocalDateTime monthlyResults = LocalDateTime.now().minusMonths(1);
        return mapper.toSaleResultDtoList(repository.findByBranchIdAndDateAfter(branchId, monthlyResults));
    }

    @Override
    public List<SaleResultDto> findByBranchIdAndDateRange(Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate) {
        return mapper.toSaleResultDtoList(repository.findByBranchIdAndDateBetween(branchId, initialDate, finalDate));
    }

    @Override
    public SaleResultDto saveResult(SaleResultDto saleResultDto) {
        SaleResult saleResult = mapper.toSaleResult(saleResultDto);

        return mapper.toSaleResultDto(repository.save(saleResult));
    }
}
