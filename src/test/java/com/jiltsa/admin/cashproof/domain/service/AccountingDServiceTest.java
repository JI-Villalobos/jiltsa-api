package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.CreateAccountingDto;
import com.jiltsa.admin.cashproof.persistence.entity.Accounting;
import com.jiltsa.admin.cashproof.persistence.mapper.AccountingMapper;
import com.jiltsa.admin.cashproof.persistence.repository.AccountingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountingDServiceTest {
    @Mock
    private AccountingRepository repository;
    private AccountingDService serviceUnderTest;

    @BeforeEach
    void setUp() {
        serviceUnderTest = new AccountingDService(repository, Mappers.getMapper(AccountingMapper.class));
    }

    @Test
    void lastRegistriesCoverTheLastSevenDaysOfTheBranch() {
        serviceUnderTest.getLastAccountingRegistries(1);

        ArgumentCaptor<LocalDateTime> since = ArgumentCaptor.forClass(LocalDateTime.class);
        verify(repository).findByBranchIdAndDateAfterOrderByDateAsc(eq(1), since.capture());
        assertThat(since.getValue()).isCloseTo(LocalDateTime.now().minusDays(7), within(1, ChronoUnit.MINUTES));
    }

    @Test
    void createMapsTheDtoOntoTheEntity() {
        LocalDateTime date = LocalDateTime.now();
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        CreateAccountingDto created = serviceUnderTest.createAccounting(new CreateAccountingDto(null, 2, 1, date));

        ArgumentCaptor<Accounting> saved = ArgumentCaptor.forClass(Accounting.class);
        verify(repository).save(saved.capture());
        assertThat(saved.getValue().getSellerId()).isEqualTo(2);
        assertThat(saved.getValue().getBranchId()).isEqualTo(1);
        assertThat(saved.getValue().getDate()).isEqualTo(date);
        assertThat(created.getSellerId()).isEqualTo(2);
    }
}
