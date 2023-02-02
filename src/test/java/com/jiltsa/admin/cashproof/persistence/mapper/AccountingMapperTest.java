package com.jiltsa.admin.cashproof.persistence.mapper;

import com.jiltsa.admin.branch.persistence.entity.Branch;
import com.jiltsa.admin.cashproof.domain.dto.AccountingDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateAccountingDto;
import com.jiltsa.admin.cashproof.persistence.entity.Accounting;
import com.jiltsa.admin.cashproof.persistence.entity.ExpenseRegistry;
import com.jiltsa.admin.cashproof.persistence.entity.IncomeRegistry;
import com.jiltsa.admin.seller.persistence.entity.Seller;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

class AccountingMapperTest {
    @Test
    void shouldMpaToAccountingDtoList() {
        //given
        AccountingMapper mapper = Mappers.getMapper(AccountingMapper.class);
        List<Accounting> accountingList = new ArrayList<>();
        Accounting accounting1 = new Accounting(1, 1, 1,
                LocalDateTime.now(),
                null,
                null,
                null,
                null
        );
        Accounting accounting2 = new Accounting(2, 1, 1,
                LocalDateTime.now(),
                null,
                null,
                null,
                null
        );
        accountingList.add(accounting1);
        accountingList.add(accounting2);

        //when
        List<AccountingDto> accountingDtoList = mapper.toAccountingDtoList(accountingList);

        //then
        assertThat(accountingDtoList.size()).isEqualTo(2);
        assertThat(accountingDtoList).isInstanceOf(ArrayList.class);
        assertThat(accountingDtoList.stream()
                .filter(accounting -> accounting.getSellerId() == 1)
                .count())
                .isEqualTo(2);
    }

    @Test
    void shouldMapperToCreateAccountingDto() {
        //given
        AccountingMapper mapper = Mappers.getMapper(AccountingMapper.class);
        Accounting accounting = new Accounting(1, 1, 1,
                LocalDateTime.now(),
                null,
                null,
                null,
                null
        );

        //when
        AccountingDto accountingDto = mapper.toAccountingDto(accounting);

        //then
        assertThat(accountingDto.getId()).isEqualTo(1);
        assertThat(accountingDto.getBranchId()).isEqualTo(1);
        assertThat(accountingDto.getSellerId()).isEqualTo(1);
    }

    @Test
    void shouldMapperToAccounting() {
        //given
        CreateAccountingDto createAccountingDto = new CreateAccountingDto(1, 1, 1);
        AccountingMapper mapper = Mappers.getMapper(AccountingMapper.class);
        LocalDateTime dateTime = LocalDateTime.now();
        Month month = dateTime.getMonth();

        //when
        Accounting accounting = mapper.toAccounting(createAccountingDto);

        //then
        assertThat(accounting.getSellerId()).isEqualTo(1);
        assertThat(accounting.getId()).isEqualTo(1);
        assertThat(accounting.getDate()).hasMonth(month);
    }
}