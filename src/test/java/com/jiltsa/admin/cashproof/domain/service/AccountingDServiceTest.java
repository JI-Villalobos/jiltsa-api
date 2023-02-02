package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.CreateAccountingDto;
import com.jiltsa.admin.cashproof.domain.repository.AccountingDRepository;
import com.jiltsa.admin.seller.domain.dto.SellerDto;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountingDServiceTest {
    @Mock
    AccountingDRepository repository;
    @InjectMocks
    private AccountingDService serviceUnderTest;
    @BeforeEach
    void setUp() {
        serviceUnderTest = new AccountingDService(repository);
    }

    @Test
    void shouldGetLastAccountingRegistries() {
        //when
        serviceUnderTest.getLastAccountingRegistries();

        //then
        verify(repository).getLastAccountingRegistries();
    }

    @Test
    void getAccountingRegistriesBetweenTwoDates() {
        //given
        LocalDateTime start = LocalDateTime.now().minusMonths(1);
        LocalDateTime end = LocalDateTime.now();

        //when
        serviceUnderTest.getAccountingRegistriesBetweenTwoDates(start, end);

        //then
        verify(repository).getAccountingRegistriesBetweenTwoDates(start, end);
    }

    @Test
    void createAccounting() {
        //given
        CreateAccountingDto createAccountingDto = new CreateAccountingDto(1, 1, 1);

        //when
        serviceUnderTest.createAccounting(createAccountingDto);

        //then
        ArgumentCaptor<CreateAccountingDto> accountingDtoArgumentCaptor =
                ArgumentCaptor.forClass(CreateAccountingDto.class);
        verify(repository).createAccounting(accountingDtoArgumentCaptor.capture());

        CreateAccountingDto captureCreateAccountingDto = accountingDtoArgumentCaptor.getValue();

        assertThat(captureCreateAccountingDto).isEqualTo(createAccountingDto);
    }
}