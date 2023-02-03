package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.IncomeTypeDto;
import com.jiltsa.admin.cashproof.domain.repository.IncomeTypeDRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@ExtendWith(MockitoExtension.class)
class IncomeTypeServiceTest {
    @Mock
    IncomeTypeDRepository repository;
    @InjectMocks
    private IncomeTypeService serviceUnderTest;

    @BeforeEach
    void setUp() {
        serviceUnderTest = new IncomeTypeService(repository);
    }

    @Test
    void shouldGetIncomeTypes() {
        //when
        serviceUnderTest.getIncomeTypes();

        //then
        Mockito.verify(repository).getIncomeTypes();
    }

    @Test
    void shouldCreateIncomeType() {
        //given
        IncomeTypeDto incomeTypeDto = new IncomeTypeDto(1, "other");

        //when
        serviceUnderTest.createIncomeType(incomeTypeDto);

        //then
        ArgumentCaptor<IncomeTypeDto> incomeTypeDtoArgumentCaptor =
                ArgumentCaptor.forClass(IncomeTypeDto.class);
        Mockito.verify(repository).createIncomeType(incomeTypeDtoArgumentCaptor.capture());

        IncomeTypeDto captureIncomeTypeDto = incomeTypeDtoArgumentCaptor.getValue();
        assertThat(captureIncomeTypeDto).isEqualTo(incomeTypeDto);
    }
}