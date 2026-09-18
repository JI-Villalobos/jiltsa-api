package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.CreateExpenseRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.ExpenseRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.ExpenseReportDto;
import com.jiltsa.admin.cashproof.persistence.entity.ExpenseRegistry;
import com.jiltsa.admin.cashproof.persistence.mapper.ExpenseRegistryMapper;
import com.jiltsa.admin.cashproof.persistence.repository.ExpenseRegistryRepository;
import com.jiltsa.admin.cashproof.persistence.repository.ExpenseResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpenseRegistryServiceTest {
    @Mock
    private ExpenseRegistryRepository repository;
    private ExpenseRegistryService serviceUnderTest;

    @BeforeEach
    void setUp() {
        serviceUnderTest = new ExpenseRegistryService(repository, Mappers.getMapper(ExpenseRegistryMapper.class));
    }

    @Test
    void registriesAreListedByAccounting() {
        when(repository.findByAccountingId(1)).thenReturn(List.of(
                new ExpenseRegistry(1, 2, "ice", Instant.now(), 30.0)));

        List<ExpenseRegistryDto> result = serviceUnderTest.getExpenseRegistries(1);

        assertThat(result).extracting(ExpenseRegistryDto::getDescription).containsExactly("ice");
    }

    @Test
    void createMapsTheDtoOntoTheEntity() {
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        serviceUnderTest.createExpenseRegistry(new CreateExpenseRegistryDto(null, 1, 2, "Expense description", 645.45));

        ArgumentCaptor<ExpenseRegistry> saved = ArgumentCaptor.forClass(ExpenseRegistry.class);
        verify(repository).save(saved.capture());
        assertThat(saved.getValue().getAccountingId()).isEqualTo(1);
        assertThat(saved.getValue().getExpenseTypeId()).isEqualTo(2);
        assertThat(saved.getValue().getAmount()).isEqualTo(645.45);
    }

    @Test
    void reportProjectionIsMappedToTheDomainDto() {
        ExpenseResult row = mock(ExpenseResult.class);
        when(row.getType()).thenReturn("SUELDOS");
        when(row.getBranchId()).thenReturn(1);
        when(row.getExpenseTypeId()).thenReturn(1);
        when(row.getTotal()).thenReturn(1200.0);
        LocalDateTime from = LocalDateTime.now().minusDays(7), to = LocalDateTime.now();
        when(repository.getExpenseReport(1, from, to)).thenReturn(List.of(row));

        List<ExpenseReportDto> report = serviceUnderTest.getExpenseReport(1, from, to);

        assertThat(report).containsExactly(new ExpenseReportDto("SUELDOS", 1, 1, 1200.0));
    }
}
