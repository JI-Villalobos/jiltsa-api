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
                1,
                "Dany"
                );

        //when
        CashWithdrawalDto cashWithdrawalDto = mapper.toCashWithdrawalDto(cashWithdrawal, "Nazas");

        //then
        assertThat(cashWithdrawalDto).isNotNull();
        assertThat(cashWithdrawalDto.getAmount()).isEqualTo(456.5);
        assertThat(cashWithdrawalDto.getBranchId()).isEqualTo(1);
        assertThat(cashWithdrawalDto.getBranch()).isEqualTo("Nazas");
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
                1,
                "Dany"
        );
        CashWithdrawal cashWithdrawal2 = new CashWithdrawal(
                2,
                LocalDateTime.now(),
                456.50,
                "Cash with concept",
                1,
                "Dany"
        );
        cashWithdrawalList.add(cashWithdrawal);
        cashWithdrawalList.add(cashWithdrawal2);

        //when
        List<CashWithdrawalDto> cashWithdrawalDtoList = mapper.toCashWithdrawalDtoList(cashWithdrawalList, "Nazas");

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
                1,
                "Dany"
        );

        //when
        CreateCashWithdrawalDto createCashWithdrawalDto = mapper.toCreateCashWithdrawalDto(cashWithdrawal, "Nazas");

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
                1,
                null
                );

        //when
        CashWithdrawal cashWithdrawal = mapper.toCashWithdrawal(createCashWithdrawalDto);

        //then
        assertThat(cashWithdrawal).isNotNull();
        // date is ignored by the mapper; CashWithdrawalService sets it on create
        assertThat(cashWithdrawal.getDate()).isNull();
        assertThat(cashWithdrawal.getBranchId()).isEqualTo(1);
    }
}