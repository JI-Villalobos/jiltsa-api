package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.domain.dto.CreditSaleDto;
import com.jiltsa.admin.cashproof.domain.repository.CreditSaleDRepository;
import com.jiltsa.admin.cashproof.persistence.entity.CreditSale;
import com.jiltsa.admin.cashproof.persistence.mapper.CreditSaleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CreditSaleRepositoryImplementation implements CreditSaleDRepository {
    private final CreditSaleRepository repository;
    private final CreditSaleMapper mapper;
    @Override
    public Optional<CreditSaleDto> getCreditSale(Integer saleId) {
        return repository.findById(saleId).map(mapper::toCreditSaleDto);
    }

    @Override
    public List<CreditSaleDto> getCreditSales(Integer branchId) {
        return mapper.toCreditSaleDtoList(repository.findByBranchId(branchId));
    }

    @Override
    public CreditSaleDto createCreditSale(CreditSaleDto creditSaleDto) {
        CreditSale creditSale = mapper.toCreditSale(creditSaleDto);
        creditSale.setIsPaid(false);

        return mapper.toCreditSaleDto(repository.save(creditSale));
    }

    @Override
    public CreditSaleDto updateCreditSale(CreditSaleDto creditSaleDto) {
        CreditSale creditSale = mapper.toCreditSale(creditSaleDto);

        return mapper.toCreditSaleDto(repository.save(creditSale));
    }
}
