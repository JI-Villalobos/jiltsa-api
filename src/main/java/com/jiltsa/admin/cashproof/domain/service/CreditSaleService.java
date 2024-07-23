package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.CreditSaleBalanceDto;
import com.jiltsa.admin.cashproof.domain.dto.CreditSaleDto;
import com.jiltsa.admin.cashproof.domain.repository.CreditSaleDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreditSaleService {
    private final CreditSaleDRepository creditSaleDRepository;

    public Optional<CreditSaleDto> getCreditSale(Integer creditSaleId){
        return creditSaleDRepository.getCreditSale(creditSaleId);
    }

    public List<CreditSaleDto> getCreditSales(Integer branchId){
        return creditSaleDRepository.getCreditSales(branchId);
    }

    public CreditSaleDto createCreditSale(CreditSaleDto creditSaleDto){
        return creditSaleDRepository.createCreditSale(creditSaleDto);
    }

    public CreditSaleDto updateCreditSale(CreditSaleDto creditSaleDto){
        return creditSaleDRepository.updateCreditSale(creditSaleDto);
    }

    public List<CreditSaleDto> getCreditSaleByPaymentStatus(Integer branchId, Boolean isPaid){
        return creditSaleDRepository.getCreditSalesByPaymentStatus(branchId, isPaid);
    }

    public Optional<CreditSaleBalanceDto> getCreditSaleBalance(Integer creditSaleId){
        return creditSaleDRepository.getBalance(creditSaleId);
    }
}
