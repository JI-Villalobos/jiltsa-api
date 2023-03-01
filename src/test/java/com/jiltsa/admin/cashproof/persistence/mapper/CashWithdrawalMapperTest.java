package com.jiltsa.admin.cashproof.persistence.mapper;

import com.jiltsa.admin.cashproof.domain.dto.CashWithdrawalDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateCashWithdrawalDto;
import com.jiltsa.admin.cashproof.persistence.entity.CashWithdrawal;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

class CashWithdrawalMapperTest {

    @Test
    void shouldMapToCashWithdrawalDto() {
        //given
        CashWithdrawalMapper mapper = Mappers.getMapper(CashWithdrawalMapper.class);
        CashWithdrawal cashWithdrawal = new CashWithdrawal(
                1,
                LocalDateTime.now(),
                456.50,
                "Cash with concept",
                "Nazas",
                "Dany"
                );

        //when
        CashWithdrawalDto cashWithdrawalDto = mapper.toCashWithdrawalDto(cashWithdrawal);

        //then
        assertThat(cashWithdrawalDto).isNotNull();
        assertThat(cashWithdrawalDto.getAmount()).isEqualTo(456.5);
    }

    @Test
    void shouldMapToCashWithdrawalDtoList() {
        //given
        CashWithdrawalMapper mapper = Mappers.getMapper(CashWithdrawalMapper.class);
        List<CashWithdrawal> cashWithdrawalList = new ArrayList<>();
        CashWithdrawal cashWithdrawal = new CashWithdrawal(
                1,
                LocalDateTime.now(),
                456.50,
                "Cash with concept",
                "Nazas",
                "Dany"
        );
        CashWithdrawal cashWithdrawal2 = new CashWithdrawal(
                2,
                LocalDateTime.now(),
                456.50,
                "Cash with concept",
                "Nazas",
                "Dany"
        );
        cashWithdrawalList.add(cashWithdrawal);
        cashWithdrawalList.add(cashWithdrawal2);

        //when
        List<CashWithdrawalDto> cashWithdrawalDtoList = mapper.toCashWithdrawalDtoList(cashWithdrawalList);

        //then
        assertThat(cashWithdrawalDtoList).isNotNull();
        assertThat(cashWithdrawalDtoList.size()).isEqualTo(2);
    }

    @Test
    void shouldMapToCreateCashWithdrawalDto() {
        //given
        CashWithdrawalMapper mapper = Mappers.getMapper(CashWithdrawalMapper.class);
        CashWithdrawal cashWithdrawal = new CashWithdrawal(
                1,
                LocalDateTime.now(),
                456.50,
                "Cash with concept",
                "Nazas",
                "Dany"
        );

        //when
        CreateCashWithdrawalDto createCashWithdrawalDto = mapper.toCreateCashWithdrawalDto(cashWithdrawal);

        //then
        assertThat(createCashWithdrawalDto).isNotNull();
        assertThat(createCashWithdrawalDto.getConcept()).isEqualTo("Cash with concept");
    }

    @Test
    void shouldMapToCashWithdrawal() {
        //given
        CashWithdrawalMapper mapper = Mappers.getMapper(CashWithdrawalMapper.class);
        CreateCashWithdrawalDto createCashWithdrawalDto = new CreateCashWithdrawalDto(
                1,
                645.5,
                "Cash concept",
                "Dany",
                "Nazas"
                );

        //when
        CashWithdrawal cashWithdrawal = mapper.toCashWithdrawal(createCashWithdrawalDto);

        //then
        assertThat(cashWithdrawal).isNotNull();
        assertThat(cashWithdrawal.getDate()).isNotNull();
    }
}