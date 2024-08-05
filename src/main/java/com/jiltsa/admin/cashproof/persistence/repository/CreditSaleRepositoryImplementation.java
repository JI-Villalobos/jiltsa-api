package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.domain.dto.CreditSaleBalanceDto;
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

    @Override
    public List<CreditSaleDto> getCreditSalesByPaymentStatus(Integer branchId, Boolean isPaid) {
        if (isPaid)
            return mapper.toCreditSaleDtoList(repository.findByBranchIdAndIsPaidTrue(branchId));
        return mapper.toCreditSaleDtoList(repository.findByBranchIdAndIsPaidFalse(branchId));
    }

    @Override
    public Optional<CreditSaleBalanceDto> getBalance(Integer creditSaleId) {
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
