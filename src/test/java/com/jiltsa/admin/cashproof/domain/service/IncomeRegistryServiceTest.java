package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.CreateIncomeRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.IncomeRegistryDto;
import com.jiltsa.admin.cashproof.persistence.entity.IncomeRegistry;
import com.jiltsa.admin.cashproof.persistence.mapper.IncomeRegistryMapper;
import com.jiltsa.admin.cashproof.persistence.repository.IncomeRegistryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IncomeRegistryServiceTest {
    @Mock
    private IncomeRegistryRepository repository;
    private IncomeRegistryService serviceUnderTest;

    @BeforeEach
    void setUp() {
        serviceUnderTest = new IncomeRegistryService(repository, Mappers.getMapper(IncomeRegistryMapper.class));
    }

    @Test
    void registriesAreListedByAccounting() {
        when(repository.findByAccountingId(1)).thenReturn(List.of(
                new IncomeRegistry(1, 1, 465.45, Instant.now(), "Income tag")));

        List<IncomeRegistryDto> result = serviceUnderTest.getIncomeRegistries(1);

        assertThat(result).extracting(IncomeRegistryDto::getTag).containsExactly("Income tag");
    }

    @Test
    void createMapsTheDtoOntoTheEntity() {
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        serviceUnderTest.createIncomeRegistry(new CreateIncomeRegistryDto(null, 1, 2, 645.45, "Income tag"));

        ArgumentCaptor<IncomeRegistry> saved = ArgumentCaptor.forClass(IncomeRegistry.class);
        verify(repository).save(saved.capture());
        assertThat(saved.getValue().getAccountingId()).isEqualTo(1);
        assertThat(saved.getValue().getIncomeTypeId()).isEqualTo(2);
        assertThat(saved.getValue().getAmount()).isEqualTo(645.45);
    }

    @Test
    void batchCreateSavesAllRegistriesAtOnce() {
        when(repository.saveAll(anyList())).thenAnswer(inv -> inv.getArgument(0));

        List<IncomeRegistryDto> created = serviceUnderTest.createIncomesRegistry(List.of(
                new CreateIncomeRegistryDto(null, 1, 1, 10.0, "a"),
                new CreateIncomeRegistryDto(null, 1, 2, 20.0, "b")));

        assertThat(created).extracting(IncomeRegistryDto::getTag).containsExactly("a", "b");
    }
}
