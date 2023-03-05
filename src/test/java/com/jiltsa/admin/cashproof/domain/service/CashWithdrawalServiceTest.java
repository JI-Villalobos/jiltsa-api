package com.jiltsa.admin.cashproof.domain.service;

import com.jiltsa.admin.cashproof.domain.dto.CreateCashWithdrawalDto;
import com.jiltsa.admin.cashproof.domain.repository.CashWithdrawalDRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CashWithdrawalServiceTest {
    @Mock
    CashWithdrawalDRepository repository;
    @InjectMocks
    private CashWithdrawalService serviceUnderTest;
    @BeforeEach
    void setUp() {
        serviceUnderTest = new CashWithdrawalService(repository);
    }

    @Test
    void shouldGetAListOfBranchCashWithdrawals() {
        //when
        serviceUnderTest.getCashWithdrawals("nazas");

        //then
        verify(repository).getWithdrawalDtoList("nazas");
    }

    @Test
    void shouldCreateACashWithdrawal() {
        //given
        CreateCashWithdrawalDto cashWithdrawalDto = new CreateCashWithdrawalDto(
                1, 354.00, "Expense", "Diana", "Nazas"
        );
        //then
        serviceUnderTest.createCashWithdrawal(cashWithdrawalDto);

        //then
        ArgumentCaptor<CreateCashWithdrawalDto> createCashWithdrawalDtoArgumentCaptor =
                ArgumentCaptor.forClass(CreateCashWithdrawalDto.class);
        verify(repository).createCashWithdrawal(createCashWithdrawalDtoArgumentCaptor.capture());

        CreateCashWithdrawalDto captureCreateCashWithdrawalDto = createCashWithdrawalDtoArgumentCaptor.getValue();

        AssertionsForClassTypes.assertThat(captureCreateCashWithdrawalDto).isEqualTo(cashWithdrawalDto);
    }
}