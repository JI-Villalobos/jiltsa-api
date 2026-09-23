package com.jiltsa.admin.sales.domain.service;

import com.jiltsa.admin.sales.domain.dto.CreateSaleDto;
import com.jiltsa.admin.sales.domain.dto.SaleBatchResultDto;
import com.jiltsa.admin.sales.persistence.mapper.SaleMapper;
import com.jiltsa.admin.sales.persistence.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SaleService {
    private final SaleRepository repository;
    private final SaleMapper mapper;

    /** All or nothing: a batch that fails part-way leaves no sales behind. */
    @Transactional
    public SaleBatchResultDto createSales(List<CreateSaleDto> sales) {
        repository.insertAll(mapper.toSaleList(sales));
        return new SaleBatchResultDto(sales.size());
    }
}
