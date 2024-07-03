package com.jiltsa.admin.cashproof.domain.service;

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

    Optional<CreditSaleDto> getCreditSale(Integer creditSaleId){
        return creditSaleDRepository.getCreditSale(creditSaleId);
    }

    List<CreditSaleDto> getCreditSales(Integer branchId){
        return creditSaleDRepository.getCreditSales(branchId);
    }

    CreditSaleDto createCreditSale(CreditSaleDto creditSaleDto){
        return creditSaleDRepository.createCreditSale(creditSaleDto);
    }

    CreditSaleDto updateCreditSale(CreditSaleDto creditSaleDto){
        return creditSaleDRepository.updateCreditSale(creditSaleDto);
    }
}
