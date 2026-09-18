package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.CashWithdrawalDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateCashWithdrawalDto;
import com.jiltsa.admin.cashproof.persistence.entity.CashWithdrawal;
import com.jiltsa.admin.cashproof.persistence.mapper.CashWithdrawalMapper;
import com.jiltsa.admin.cashproof.persistence.repository.CashWithdrawalRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CashWithdrawalServiceTest {
    @Mock
    private CashWithdrawalRepository repository;
    private CashWithdrawalService serviceUnderTest;

    @BeforeEach
    void setUp() {
        serviceUnderTest = new CashWithdrawalService(repository, Mappers.getMapper(CashWithdrawalMapper.class));
    }

    @Test
    void withdrawalsAreListedByBranch() {
        when(repository.findByBranch("nazas")).thenReturn(List.of(
                new CashWithdrawal(LocalDateTime.now(), 100.0, "gas", "nazas", "Diana")));

        List<CashWithdrawalDto> result = serviceUnderTest.getCashWithdrawals("nazas");

        assertThat(result).extracting(CashWithdrawalDto::getConcept).containsExactly("gas");
    }

    @Test
    void createStampsTheWithdrawalWithMexicoCityTime() {
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        serviceUnderTest.createCashWithdrawal(new CreateCashWithdrawalDto(null, 250.0, "rent", "Diana", "nazas"));

        ArgumentCaptor<CashWithdrawal> saved = ArgumentCaptor.forClass(CashWithdrawal.class);
        verify(repository).save(saved.capture());
        assertThat(saved.getValue().getAmount()).isEqualTo(250.0);
        assertThat(saved.getValue().getDate()).isCloseTo(
                ZonedDateTime.now(ZoneId.of("America/Mexico_City")).toLocalDateTime(), within(1, ChronoUnit.MINUTES));
    }

    @Test
    void currentRegistriesCoverTheLastDay() {
        serviceUnderTest.getCurrentCashWithdrawalsRegistries("nazas");

        ArgumentCaptor<LocalDateTime> since = ArgumentCaptor.forClass(LocalDateTime.class);
        verify(repository).findByBranchAndDateAfter(eq("nazas"), since.capture());
        assertThat(since.getValue()).isCloseTo(LocalDateTime.now().minusDays(1), within(1, ChronoUnit.MINUTES));
    }
}
