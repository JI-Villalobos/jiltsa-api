package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.ExpenseTypeDto;
import com.jiltsa.admin.cashproof.domain.repository.ExpenseTypeDRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseTypeServiceTest {

    @Mock
    ExpenseTypeDRepository expenseTypeDRepository;
    @InjectMocks
    private ExpenseTypeService serviceUnderTest;

    @BeforeEach
    void setUp() {
        serviceUnderTest = new ExpenseTypeService(expenseTypeDRepository);
    }

    @Test
    void shouldGetExpenseTypes() {
        //when
        serviceUnderTest.getExpenseTypes();

        //then
        verify(expenseTypeDRepository).getExpenseTypes();
    }

    @Test
    void shouldCreateExpenseType() {
        //given
        ExpenseTypeDto expenseTypeDto = new ExpenseTypeDto(1, "other");

        //when
        serviceUnderTest.createExpenseType(expenseTypeDto);

        //then
        ArgumentCaptor<ExpenseTypeDto> expenseTypeDtoArgumentCaptor =
                ArgumentCaptor.forClass(ExpenseTypeDto.class);
        verify(expenseTypeDRepository).createExpenseType(expenseTypeDtoArgumentCaptor.capture());

        ExpenseTypeDto captureExpenseTypeDto = expenseTypeDtoArgumentCaptor.getValue();

        assertThat(captureExpenseTypeDto).isEqualTo(expenseTypeDto);
    }
}