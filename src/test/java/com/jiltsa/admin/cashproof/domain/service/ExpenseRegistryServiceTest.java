package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.CreateExpenseRegistryDto;
import com.jiltsa.admin.cashproof.domain.repository.ExpenseRegistryDRepository;
import com.jiltsa.admin.cashproof.domain.repository.ExpenseTypeDRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseRegistryServiceTest {
    @Mock
    ExpenseRegistryDRepository repository;
    @InjectMocks
    private ExpenseRegistryService serviceUnderUnderTest;

    @BeforeEach
    void setUp() {
        serviceUnderUnderTest = new ExpenseRegistryService(repository);
    }

    @Test
    void shouldGetExpenseRegistries() {
        //when
        serviceUnderUnderTest.getExpenseRegistries(1);

        //then
        verify(repository).getExpenseRegistries(1);
    }

    @Test
    void shouldCreateExpenseRegistry() {
        //given
        CreateExpenseRegistryDto createExpenseRegistryDto = new CreateExpenseRegistryDto(1, 1, 1, "Expense DEscription", 645.45);

        //when
        serviceUnderUnderTest.createExpenseRegistry(createExpenseRegistryDto);

        //then
        ArgumentCaptor<CreateExpenseRegistryDto> createExpenseRegistryDtoArgumentCaptor =
                ArgumentCaptor.forClass(CreateExpenseRegistryDto.class);
        verify(repository).createExpenseRegistry(createExpenseRegistryDtoArgumentCaptor.capture());

        CreateExpenseRegistryDto captureExpenseRegistryDto = createExpenseRegistryDtoArgumentCaptor.getValue();

        assertThat(captureExpenseRegistryDto).isEqualTo(captureExpenseRegistryDto);
    }
}