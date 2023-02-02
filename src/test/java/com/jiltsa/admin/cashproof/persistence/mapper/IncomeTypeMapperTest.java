package com.jiltsa.admin.cashproof.persistence.mapper;

import com.jiltsa.admin.cashproof.domain.dto.IncomeTypeDto;
import com.jiltsa.admin.cashproof.persistence.entity.IncomeType;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

class IncomeTypeMapperTest {

    @Test
    void shouldMapperToIncomeTypeDto() {
        //given
        IncomeTypeMapper mapper = Mappers.getMapper(IncomeTypeMapper.class);
        IncomeType incomeType = new IncomeType(1, "other", null);

        //when
        IncomeTypeDto incomeTypeDto = mapper.toIncomeTypeDto(incomeType);

        //then
        assertThat(incomeTypeDto.getType()).isEqualTo("other");
    }

    @Test
    void shouldMapperToIncomeTypeDtoList() {
        //given
        IncomeTypeMapper mapper = Mappers.getMapper(IncomeTypeMapper.class);
        IncomeType incomeType = new IncomeType(1, "other", null);
        IncomeType incomeType2 = new IncomeType(1, "other", null);
        List<IncomeType> incomeTypeList = new ArrayList<>();

        incomeTypeList.add(incomeType);
        incomeTypeList.add(incomeType2);

        //when
        List<IncomeTypeDto> incomeTypeDtoList = mapper.incomeTypeDtoList(incomeTypeList);

        //then
        assertThat(incomeTypeDtoList.size()).isEqualTo(2);
    }

    @Test
    void shouldMapperToIncomeType() {
        //given
        IncomeTypeMapper mapper = Mappers.getMapper(IncomeTypeMapper.class);
        IncomeTypeDto incomeTypeDto = new IncomeTypeDto(1, "other");

        //when
        IncomeType incomeType = mapper.toIncomeType(incomeTypeDto);

        //then
        assertThat(incomeType).isInstanceOf(IncomeType.class);
        assertThat(incomeType.getType()).isEqualTo("other");
    }
}