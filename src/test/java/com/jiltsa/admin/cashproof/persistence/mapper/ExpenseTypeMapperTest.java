package com.jiltsa.admin.cashproof.persistence.mapper;

import com.jiltsa.admin.cashproof.domain.dto.ExpenseTypeDto;
import com.jiltsa.admin.cashproof.persistence.entity.ExpenseType;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

class ExpenseTypeMapperTest {

    @Test
    void shouldMapToExpenseTypeDtoList() {
        //given
        ExpenseTypeMapper mapper = Mappers.getMapper(ExpenseTypeMapper.class);
        List<ExpenseType> expenseTypeList = new ArrayList<>();
        ExpenseType expenseType = new ExpenseType(1, "other", null);
        ExpenseType expenseType2 = new ExpenseType(1, "other", null);

        expenseTypeList.add(expenseType);
        expenseTypeList.add(expenseType2);

        //when
        List<ExpenseTypeDto> expenseTypeDtoList = mapper.toExpenseTypeDtoList(expenseTypeList);

        //then
        assertThat(expenseTypeDtoList.size()).isEqualTo(2);
    }

    @Test
    void shouldMapperToExpenseTypeDto() {
        //given
        ExpenseTypeMapper mapper = Mappers.getMapper(ExpenseTypeMapper.class);
        ExpenseType expenseType = new ExpenseType(1, "other", null);

        //when
        ExpenseTypeDto expenseTypeDto = mapper.toExpenseTypeDto(expenseType);

        //then
        assertThat(expenseTypeDto.getType()).isEqualTo("other");
    }

    @Test
    void toExpenseType() {
        //given
        ExpenseTypeMapper mapper = Mappers.getMapper(ExpenseTypeMapper.class);
        ExpenseTypeDto expenseTypeDto = new ExpenseTypeDto(1, "other");

        //when
        ExpenseType expenseType = mapper.toExpenseType(expenseTypeDto);

        //then
        assertThat(expenseType).isInstanceOf(ExpenseType.class);
        assertThat(expenseType.getType()).isEqualTo("other");
    }
}