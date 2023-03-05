package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.CashWithdrawalDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateCashWithdrawalDto;
import com.jiltsa.admin.cashproof.domain.repository.CashWithdrawalDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CashWithdrawalService {
    private final CashWithdrawalDRepository cashWithdrawalDRepository;

    public List<CashWithdrawalDto> getCashWithdrawals(String branch){
        return cashWithdrawalDRepository.getWithdrawalDtoList(branch);
    }

    public CreateCashWithdrawalDto createCashWithdrawal(CreateCashWithdrawalDto createCashWithdrawalDto){
        return cashWithdrawalDRepository.createCashWithdrawal(createCashWithdrawalDto);
    }
}
