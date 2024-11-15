package com.jiltsa.admin.cashproof.domain.repository;

import com.jiltsa.admin.cashproof.domain.dto.CashWithdrawalDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateCashWithdrawalDto;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CashWithdrawalDRepository {
    List<CashWithdrawalDto> getWithdrawalDtoList(String branch);
    CreateCashWithdrawalDto createCashWithdrawal(CreateCashWithdrawalDto createCashWithdrawalDto);
    List<CashWithdrawalDto> getCurrentRegistries(String branch);
    Page<CashWithdrawalDto> getLatestRegistries(
            int page, int elements, String sortBy, String sortDirection,
            String branch);

    Page<CashWithdrawalDto> getRegistriesByTagAndDateBetween(
            int page, int elements, String sortBy, String sortDirection,
            String branch, String concept, LocalDateTime start, LocalDateTime finish);

    Page<CashWithdrawalDto> getRegistriesByDateBetween(
            int page, int elements, String sortBy, String sortDirection,
            String branch, LocalDateTime start, LocalDateTime finish);

    Optional<CashWithdrawalDto> getCashWithdrawal(Integer cashWithdrawalId);
    CashWithdrawalDto updateCashWithdrawal(CashWithdrawalDto cashWithdrawalDto);
    void deleteCashWithdrawal(Integer cashWithdrawalId);
}
