package com.jiltsa.admin.cashproof.domain.repository;

import com.jiltsa.admin.cashproof.domain.dto.CashWithdrawalDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateCashWithdrawalDto;

import java.util.List;

public interface CashWithdrawalDRepository {
    List<CashWithdrawalDto> getWithdrawalDtoList(String branch);
    CreateCashWithdrawalDto createCashWithdrawal(CreateCashWithdrawalDto createCashWithdrawalDto);
}
