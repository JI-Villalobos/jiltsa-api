package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.branch.domain.service.BranchService;
import com.jiltsa.admin.cashproof.domain.dto.CashWithdrawalDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateCashWithdrawalDto;
import com.jiltsa.admin.cashproof.persistence.entity.CashWithdrawal;
import com.jiltsa.admin.cashproof.persistence.mapper.CashWithdrawalMapper;
import com.jiltsa.admin.cashproof.persistence.repository.CashWithdrawalRepository;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
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
    private final BranchService branchService;

    public List<CashWithdrawalDto> getCashWithdrawals(Integer branchId) {
        return mapper.toCashWithdrawalDtoList(repository.findByBranchId(branchId), branchName(branchId));
    }

    @Transactional
    public CreateCashWithdrawalDto createCashWithdrawal(CreateCashWithdrawalDto createCashWithdrawalDto) {
        String branchName = branchName(createCashWithdrawalDto.getBranchId());
        CashWithdrawal cashWithdrawal = mapper.toCashWithdrawal(createCashWithdrawalDto);
        //user of this app are only from mx
        ZonedDateTime mxTime = ZonedDateTime.now(ZoneId.of("America/Mexico_City"));
        cashWithdrawal.setDate(mxTime.toLocalDateTime());
        return mapper.toCreateCashWithdrawalDto(repository.save(cashWithdrawal), branchName);
    }

    public List<CashWithdrawalDto> getCurrentCashWithdrawalsRegistries(Integer branchId) {
        LocalDateTime date = LocalDateTime.now().minusDays(1);
        return mapper.toCashWithdrawalDtoList(repository.findByBranchIdAndDateAfter(branchId, date), branchName(branchId));
    }

    public Page<CashWithdrawalDto> getLatestMonthRegistries(Pageable pageable, Integer branchId) {
        LocalDateTime start = LocalDateTime.now().minusMonths(1);
        return mapper.toCashWithdrawalDtoPage(
                repository.findByBranchIdAndDateBetween(branchId, start, LocalDateTime.now(), pageable), branchName(branchId));
    }

    public Page<CashWithdrawalDto> getRegistriesByTagAndDate(Pageable pageable, Integer branchId, String concept, LocalDateTime start,
                                                                    LocalDateTime finish) {
        return mapper.toCashWithdrawalDtoPage(repository.findByBranchIdAndConceptContainingAndDateBetween(
                branchId, concept, start, finish, pageable), branchName(branchId));
    }

    public Page<CashWithdrawalDto> getRegistriesByDateBetween(Pageable pageable, Integer branchId, LocalDateTime start, LocalDateTime finish) {
        return mapper.toCashWithdrawalDtoPage(
                repository.findByBranchIdAndDateBetween(branchId, start, finish, pageable), branchName(branchId));
    }

    public Optional<CashWithdrawalDto> getCashWithdrawal(Integer cashWithdrawalId) {
        return repository.findById(cashWithdrawalId)
                .map(cashWithdrawal -> mapper.toCashWithdrawalDto(cashWithdrawal, branchName(cashWithdrawal.getBranchId())));
    }

    @Transactional
    public CashWithdrawalDto updateCashWithdrawal(CashWithdrawalDto cashWithdrawalDto) {
        String branchName = branchName(cashWithdrawalDto.getBranchId());
        CashWithdrawal cashWithdrawal = mapper.toCashWithdrawal(cashWithdrawalDto);

        return mapper.toCashWithdrawalDto(repository.save(cashWithdrawal), branchName);
    }

    @Transactional
    public void deleteCashWithdrawal(Integer cashWithdrawalId) {
        repository.deleteById(cashWithdrawalId);
    }

    /** Also rejects unknown branches with a 404 instead of a foreign key error or an empty list. */
    private String branchName(Integer branchId) {
        return branchService.getById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", branchId))
                .getName();
    }
}
