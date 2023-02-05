package com.jiltsa.admin.cashproof.persistence.mapper;

import com.jiltsa.admin.cashproof.domain.dto.CreateExpenseRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.ExpenseRegistryDto;
import com.jiltsa.admin.cashproof.persistence.entity.ExpenseRegistry;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

class ExpenseRegistryMapperTest {
    @Test
    void shouldMapToExpenseRegistryDto() {
        //given
        ExpenseRegistryMapper mapper = Mappers.getMapper((ExpenseRegistryMapper.class));
        ExpenseRegistry expenseRegistry = new ExpenseRegistry(
                1,
                1,
                1,
                "Expense Registry desc",
                Instant.now(),
                456.6,
                null,
                null);

        //when
        ExpenseRegistryDto expenseRegistryDto = mapper.toExpenseRegistryDto(expenseRegistry);

        //then
        assertThat(expenseRegistryDto).isNotNull();
        assertThat(expenseRegistryDto.getDescription()).isEqualTo("Expense Registry desc");
        assertThat(expenseRegistryDto.getTime()).isInstanceOf(Instant.class);
    }

    @Test
    void shouldMapToExpenseRegistryDtoList() {
        //given
        ExpenseRegistryMapper mapper = Mappers.getMapper((ExpenseRegistryMapper.class));
        List<ExpenseRegistry> expenseRegistryList = new ArrayList<>();
        ExpenseRegistry expenseRegistry = new ExpenseRegistry(
                1,
                1,
                1,
                "Expense Registry desc",
                Instant.now(),
                456.6,
                null,
                null);
        ExpenseRegistry expenseRegistry2 = new ExpenseRegistry(
                1,
                1,
                1,
                "Expense Registry desc",
                Instant.now(),
                456.6,
                null,
                null);
        expenseRegistryList.add(expenseRegistry);
        expenseRegistryList.add(expenseRegistry2);

        //when
        List<ExpenseRegistryDto> expenseRegistryDtoList = mapper.toExpenseRegistryDtoList(expenseRegistryList);

        //then
        assertThat(expenseRegistryDtoList).isNotNull();
        assertThat(expenseRegistryDtoList.size()).isEqualTo(2);

    }

    @Test
    void shouldMapToCreateExpenseRegistryDto() {
        //given
        ExpenseRegistryMapper mapper = Mappers.getMapper((ExpenseRegistryMapper.class));
        ExpenseRegistry expenseRegistry = new ExpenseRegistry(
                1,
                1,
                1,
                "Expense Registry desc",
                Instant.now(),
                456.6,
                null,
                null);

        //when
        CreateExpenseRegistryDto createExpenseRegistryDto = mapper.toCreateExpenseRegistryDto(expenseRegistry);

        //then
        assertThat(createExpenseRegistryDto).isNotNull();
        assertThat(createExpenseRegistryDto.getDescription()).isEqualTo("Expense Registry desc");
    }

    @Test
    void shouldMapToExpenseRegistry(){
        //given
        ExpenseRegistryMapper mapper = Mappers.getMapper((ExpenseRegistryMapper.class));
        CreateExpenseRegistryDto createExpenseRegistryDto = new CreateExpenseRegistryDto(
                1,
                1,
                "Expense Registry desc",
                245.65);

        //when
        ExpenseRegistry expenseRegistry = mapper.toExpenseRegistry(createExpenseRegistryDto);

        //then
        assertThat(expenseRegistry).isNotNull();
        assertThat(expenseRegistry).isInstanceOf(ExpenseRegistry.class);
        assertThat(expenseRegistry.getTime()).isNotNull();
    }
}