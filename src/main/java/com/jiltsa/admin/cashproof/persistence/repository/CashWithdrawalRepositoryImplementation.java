package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.domain.dto.CashWithdrawalDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateCashWithdrawalDto;
import com.jiltsa.admin.cashproof.domain.repository.CashWithdrawalDRepository;
import com.jiltsa.admin.cashproof.persistence.entity.CashWithdrawal;
import com.jiltsa.admin.cashproof.persistence.mapper.CashWithdrawalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CashWithdrawalRepositoryImplementation implements CashWithdrawalDRepository {
    private final CashWithdrawalRepository repository;
    private final CashWithdrawalMapper mapper;
    @Override
    public List<CashWithdrawalDto> getWithdrawalDtoList(String branch) {
        return mapper.toCashWithdrawalDtoList(repository.findByBranch(branch));
    }

    @Override
    public CreateCashWithdrawalDto createCashWithdrawal(CreateCashWithdrawalDto createCashWithdrawalDto) {
        CashWithdrawal cashWithdrawal = mapper.toCashWithdrawal(createCashWithdrawalDto);
        return mapper.toCreateCashWithdrawalDto(repository.save(cashWithdrawal));
    }

    @Override
    public List<CashWithdrawalDto> getCurrentRegistries(String branch) {
        LocalDateTime date = LocalDateTime.now().minusDays(1);
        return mapper.toCashWithdrawalDtoList(repository.findByBranchAndDateAfter(branch, date));
    }

    @Override
    public Page<CashWithdrawalDto> getLatestRegistries(int page, int elements, String sortBy, String sortDirection, String branch) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        Pageable pageRequest = PageRequest.of(page, elements,sort);
        LocalDateTime start = LocalDateTime.now().minusMonths(1);
        return mapper.toCashwithdrawalPage(repository.findByBranchAndDateBetween(branch, start, LocalDateTime.now(), pageRequest));
    }

    @Override
    public Page<CashWithdrawalDto> getRegistriesByTagAndDateBetween(int page, int elements, String sortBy, String sortDirection,
                                                                    String branch, String concept, LocalDateTime start,
                                                                    LocalDateTime finish) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);

        return mapper.toCashwithdrawalPage(repository.findByBranchAndConceptContainingAndDateBetween(
                branch, concept, start, finish, pageRequest));
    }

    @Override
    public Page<CashWithdrawalDto> getRegistriesByDateBetween(int page, int elements, String sortBy, String sortDirection,
                                                              String branch, LocalDateTime start, LocalDateTime finish) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);

        return mapper.toCashwithdrawalPage(repository.findByBranchAndDateBetween(branch, start, finish, pageRequest));
    }

}
