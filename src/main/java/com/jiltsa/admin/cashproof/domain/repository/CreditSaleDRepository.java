package com.jiltsa.admin.cashproof.domain.repository;

import com.jiltsa.admin.cashproof.domain.dto.CreditSaleDto;

import java.util.List;
import java.util.Optional;

public interface CreditSaleDRepository {
    Optional<CreditSaleDto> getCreditSale(Integer saleId);
    List<CreditSaleDto> getCreditSales(Integer branchId);
    CreditSaleDto createCreditSale(CreditSaleDto creditSaleDto);
    CreditSaleDto updateCreditSale(CreditSaleDto creditSaleDto);
    List<CreditSaleDto> getCreditSalesByPaymentStatus(Integer branchId, Boolean isPaid);
}
