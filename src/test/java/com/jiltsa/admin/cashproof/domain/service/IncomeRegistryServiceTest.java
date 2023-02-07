package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.CreateIncomeRegistryDto;
import com.jiltsa.admin.cashproof.domain.repository.IncomeRegistryDRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class IncomeRegistryServiceTest {
    @Mock
    IncomeRegistryDRepository repository;
    @InjectMocks
    private IncomeRegistryService incomeRegistryServiceUnderTest;
    @BeforeEach
    void setUp() {
        incomeRegistryServiceUnderTest = new IncomeRegistryService(repository);
    }

    @Test
    void shouldGetIncomeRegistries() {
        //when
        incomeRegistryServiceUnderTest.getIncomeRegistries(1);

        //then
        verify(repository).getIncomeRegistries(1);
    }

    @Test
    void shouldCreateIncomeRegistry() {
        //given
        CreateIncomeRegistryDto createIncomeRegistryDto = new CreateIncomeRegistryDto(1, 1, 1, 645.45, "Income tag");

        //when
        incomeRegistryServiceUnderTest.createIncomeRegistry(createIncomeRegistryDto);

        //then
        ArgumentCaptor<CreateIncomeRegistryDto> createIncomeRegistryDtoArgumentCaptor =
                ArgumentCaptor.forClass(CreateIncomeRegistryDto.class);
        verify(repository).createIncomeRegistry(createIncomeRegistryDtoArgumentCaptor.capture());

        CreateIncomeRegistryDto captureCreateIncomeRegistryDto = createIncomeRegistryDtoArgumentCaptor.getValue();

        assertThat(captureCreateIncomeRegistryDto).isEqualTo(createIncomeRegistryDto);
    }
}