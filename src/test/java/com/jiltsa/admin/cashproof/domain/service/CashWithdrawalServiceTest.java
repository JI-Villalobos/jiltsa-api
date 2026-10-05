package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.branch.domain.dto.BranchDto;
import com.jiltsa.admin.branch.domain.service.BranchService;
import com.jiltsa.admin.cashproof.domain.dto.CashWithdrawalDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateCashWithdrawalDto;
import com.jiltsa.admin.cashproof.persistence.entity.CashWithdrawal;
import com.jiltsa.admin.cashproof.persistence.mapper.CashWithdrawalMapper;
import com.jiltsa.admin.cashproof.persistence.repository.CashWithdrawalRepository;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CashWithdrawalServiceTest {
    private static final BranchDto NAZAS = new BranchDto(1, "Nazas", true);

    @Mock
    private CashWithdrawalRepository repository;
    @Mock
    private BranchService branchService;
    private CashWithdrawalService serviceUnderTest;

    @BeforeEach
    void setUp() {
        serviceUnderTest = new CashWithdrawalService(repository, Mappers.getMapper(CashWithdrawalMapper.class), branchService);
    }

    @Test
    void withdrawalsAreListedByBranchIdWithTheBranchName() {
        when(branchService.getById(1)).thenReturn(Optional.of(NAZAS));
        when(repository.findByBranchId(1)).thenReturn(List.of(
                new CashWithdrawal(LocalDateTime.now(), 100.0, "gas", 1, "Diana")));

        List<CashWithdrawalDto> result = serviceUnderTest.getCashWithdrawals(1);

        assertThat(result).extracting(CashWithdrawalDto::getConcept).containsExactly("gas");
        assertThat(result).extracting(CashWithdrawalDto::getBranchId).containsExactly(1);
        assertThat(result).extracting(CashWithdrawalDto::getBranch).containsExactly("Nazas");
    }

    @Test
    void createStampsTheWithdrawalWithMexicoCityTime() {
        when(branchService.getById(1)).thenReturn(Optional.of(NAZAS));
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        CreateCashWithdrawalDto created = serviceUnderTest.createCashWithdrawal(
                new CreateCashWithdrawalDto(null, 250.0, "rent", "Diana", 1, null));

        ArgumentCaptor<CashWithdrawal> saved = ArgumentCaptor.forClass(CashWithdrawal.class);
        verify(repository).save(saved.capture());
        assertThat(saved.getValue().getAmount()).isEqualTo(250.0);
        assertThat(saved.getValue().getBranchId()).isEqualTo(1);
        assertThat(saved.getValue().getDate()).isCloseTo(
                ZonedDateTime.now(ZoneId.of("America/Mexico_City")).toLocalDateTime(), within(1, ChronoUnit.MINUTES));
        assertThat(created.getBranch()).isEqualTo("Nazas");
    }

    @Test
    void createRejectsAnUnknownBranch() {
        when(branchService.getById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> serviceUnderTest.createCashWithdrawal(
                new CreateCashWithdrawalDto(null, 250.0, "rent", "Diana", 99, null)))
                .isInstanceOf(ResourceNotFoundException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void currentRegistriesCoverTheLastDay() {
        when(branchService.getById(1)).thenReturn(Optional.of(NAZAS));

        serviceUnderTest.getCurrentCashWithdrawalsRegistries(1);

        ArgumentCaptor<LocalDateTime> since = ArgumentCaptor.forClass(LocalDateTime.class);
        verify(repository).findByBranchIdAndDateAfter(eq(1), since.capture());
        assertThat(since.getValue()).isCloseTo(LocalDateTime.now().minusDays(1), within(1, ChronoUnit.MINUTES));
    }
}
