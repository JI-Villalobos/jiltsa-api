package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.CreditSaleBalanceDto;
import com.jiltsa.admin.cashproof.domain.dto.CreditSaleDto;
import com.jiltsa.admin.cashproof.persistence.entity.CreditSale;
import com.jiltsa.admin.cashproof.persistence.mapper.CreditSaleMapper;
import com.jiltsa.admin.cashproof.persistence.repository.CreditSaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CreditSaleService {
    private final CreditSaleRepository repository;
    private final CreditSaleMapper mapper;

    public Optional<CreditSaleDto> getCreditSale(Integer saleId) {
        return repository.findById(saleId).map(mapper::toCreditSaleDto);
    }

    public List<CreditSaleDto> getCreditSales(Integer branchId) {
        return mapper.toCreditSaleDtoList(repository.findByBranchId(branchId));
    }

    @Transactional
    public CreditSaleDto createCreditSale(CreditSaleDto creditSaleDto) {
        CreditSale creditSale = mapper.toCreditSale(creditSaleDto);
        creditSale.setIsPaid(false);

        return mapper.toCreditSaleDto(repository.save(creditSale));
    }

    @Transactional
    public CreditSaleDto updateCreditSale(CreditSaleDto creditSaleDto) {
        CreditSale creditSale = mapper.toCreditSale(creditSaleDto);

        return mapper.toCreditSaleDto(repository.save(creditSale));
    }

    public List<CreditSaleDto> getCreditSaleByPaymentStatus(Integer branchId, Boolean isPaid) {
        if (isPaid)
            return mapper.toCreditSaleDtoList(repository.findByBranchIdAndIsPaidTrue(branchId));
        return mapper.toCreditSaleDtoList(repository.findByBranchIdAndIsPaidFalse(branchId));
    }

    public Optional<CreditSaleBalanceDto> getCreditSaleBalance(Integer creditSaleId) {
        Optional<CreditSale> creditSale = repository.findById(creditSaleId);
        if (creditSale.isPresent()){
          Integer payments = creditSale.get().getPartials().size();
          Double totalPayments = creditSale.get().getPartials()
                  .stream().reduce(0.0, (a, b) -> a + b.getAmount(), Double::sum);
          Double outstandingBalance = creditSale.get().getAmount() - totalPayments;

          CreditSaleBalanceDto creditSaleBalanceDto =
                  new CreditSaleBalanceDto(payments, totalPayments, outstandingBalance);
          return Optional.of(creditSaleBalanceDto);
        }
        return Optional.empty();
    }
}
