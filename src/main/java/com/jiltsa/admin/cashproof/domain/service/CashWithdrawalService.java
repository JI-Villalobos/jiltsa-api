package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.CashWithdrawalDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateCashWithdrawalDto;
import com.jiltsa.admin.cashproof.persistence.entity.CashWithdrawal;
import com.jiltsa.admin.cashproof.persistence.mapper.CashWithdrawalMapper;
import com.jiltsa.admin.cashproof.persistence.repository.CashWithdrawalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CashWithdrawalService {
    private final CashWithdrawalRepository repository;
    private final CashWithdrawalMapper mapper;

    public List<CashWithdrawalDto> getCashWithdrawals(String branch) {
        return mapper.toCashWithdrawalDtoList(repository.findByBranch(branch));
    }

    @Transactional
    public CreateCashWithdrawalDto createCashWithdrawal(CreateCashWithdrawalDto createCashWithdrawalDto) {
        CashWithdrawal cashWithdrawal = mapper.toCashWithdrawal(createCashWithdrawalDto);
        //user of this app are only from mx
        ZonedDateTime mxTime = ZonedDateTime.now(ZoneId.of("America/Mexico_City"));
        cashWithdrawal.setDate(mxTime.toLocalDateTime());
        return mapper.toCreateCashWithdrawalDto(repository.save(cashWithdrawal));
    }

    public List<CashWithdrawalDto> getCurrentCashWithdrawalsRegistries(String branch) {
        LocalDateTime date = LocalDateTime.now().minusDays(1);
        return mapper.toCashWithdrawalDtoList(repository.findByBranchAndDateAfter(branch, date));
    }

    public Page<CashWithdrawalDto> getLatestMonthRegistries(Pageable pageable, String branch) {
        LocalDateTime start = LocalDateTime.now().minusMonths(1);
        return mapper.toCashWithdrawalDtoPage(repository.findByBranchAndDateBetween(branch, start, LocalDateTime.now(), pageable));
    }

    public Page<CashWithdrawalDto> getRegistriesByTagAndDate(Pageable pageable, String branch, String concept, LocalDateTime start,
                                                                    LocalDateTime finish) {
        return mapper.toCashWithdrawalDtoPage(repository.findByBranchAndConceptContainingAndDateBetween(
                branch, concept, start, finish, pageable));
    }

    public Page<CashWithdrawalDto> getRegistriesByDateBetween(Pageable pageable, String branch, LocalDateTime start, LocalDateTime finish) {
        return mapper.toCashWithdrawalDtoPage(repository.findByBranchAndDateBetween(branch, start, finish, pageable));
    }

    public Optional<CashWithdrawalDto> getCashWithdrawal(Integer cashWithdrawalId) {
        return repository.findById(cashWithdrawalId).map(mapper::toCashWithdrawalDto);
    }

    @Transactional
    public CashWithdrawalDto updateCashWithdrawal(CashWithdrawalDto cashWithdrawalDto) {
        CashWithdrawal cashWithdrawal = mapper.toCashWithdrawal(cashWithdrawalDto);

        return mapper.toCashWithdrawalDto(repository.save(cashWithdrawal));
    }

    @Transactional
    public void deleteCashWithdrawal(Integer cashWithdrawalId) {
        repository.deleteById(cashWithdrawalId);
    }
}
