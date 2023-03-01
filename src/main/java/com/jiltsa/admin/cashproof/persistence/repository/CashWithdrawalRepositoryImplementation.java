package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.domain.dto.CashWithdrawalDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateCashWithdrawalDto;
import com.jiltsa.admin.cashproof.domain.repository.CashWithdrawalDRepository;
import com.jiltsa.admin.cashproof.persistence.entity.CashWithdrawal;
import com.jiltsa.admin.cashproof.persistence.mapper.CashWithdrawalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CashWithdrawalRepositoryImplementation implements CashWithdrawalDRepository {
    private final CashWithdrawalRepository repository;
    private final CashWithdrawalMapper mapper;
    @Override
    public List<CashWithdrawalDto> getWithdrawalDtoList() {
        return mapper.toCashWithdrawalDtoList(repository.findAll());
    }

    @Override
    public CreateCashWithdrawalDto createCashWithdrawal(CreateCashWithdrawalDto createCashWithdrawalDto) {
        CashWithdrawal cashWithdrawal = mapper.toCashWithdrawal(createCashWithdrawalDto);
        return mapper.toCreateCashWithdrawalDto(repository.save(cashWithdrawal));
    }
}
