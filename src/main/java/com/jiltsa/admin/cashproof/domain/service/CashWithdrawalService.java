package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.CashWithdrawalDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateCashWithdrawalDto;
import com.jiltsa.admin.cashproof.domain.repository.CashWithdrawalDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public List<CashWithdrawalDto> getCurrentCashWithdrawalsRegistries(String branch){
        return cashWithdrawalDRepository.getCurrentRegistries(branch);
    }

    public Page<CashWithdrawalDto> getLatestMonthRegistries(
            int page, int elements, String sortBy, String sortDirection, String branch){
        return cashWithdrawalDRepository.getLatestRegistries(page, elements, sortBy, sortDirection, branch);
    }

    public Page<CashWithdrawalDto> getRegistriesByTagAndDate(
            int page, int elements, String sortBy, String sortDirection, String branch,
            String concept, LocalDateTime dateTime){
        return cashWithdrawalDRepository.getRegistriesByTagAndDate(page, elements, sortBy, sortDirection,
                branch, concept, dateTime);
    }

    public Page<CashWithdrawalDto> getRegistriesByDate(
            int page, int elements, String sortBy, String sortDirection, String branch,
            LocalDateTime dateTime){
        return cashWithdrawalDRepository.getRegistriesByDate(page, elements, sortBy, sortDirection,
                branch, dateTime);
    }
}
