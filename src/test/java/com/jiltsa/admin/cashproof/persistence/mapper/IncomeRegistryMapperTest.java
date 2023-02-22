package com.jiltsa.admin.cashproof.persistence.mapper;

import com.jiltsa.admin.cashproof.domain.dto.CreateIncomeRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.IncomeRegistryDto;
import com.jiltsa.admin.cashproof.persistence.entity.IncomeRegistry;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class IncomeRegistryMapperTest {

    @Test
    void shouldMapToIncomeRegistryDto() {
        //given
        IncomeRegistryMapper mapper = Mappers.getMapper(IncomeRegistryMapper.class);
        IncomeRegistry incomeRegistry = new IncomeRegistry(1,
                1,
                1,645.45,
                Instant.now(),
                "Income tag",
                null,
                null
        );

        //when
        IncomeRegistryDto incomeRegistryDto = mapper.toIncomeRegistryDto(incomeRegistry);

        //then
        assertThat(incomeRegistryDto).isNotNull();
        assertThat(incomeRegistryDto.getTag()).isEqualTo("Income tag");
        assertThat(incomeRegistryDto.getTime()).isInstanceOf(Instant.class);
    }

    @Test
    void shouldMapToIncomeRegistryDtoList() {
        //given
        IncomeRegistryMapper mapper = Mappers.getMapper(IncomeRegistryMapper.class);
        List<IncomeRegistry> incomeRegistryList = new ArrayList<>();
        IncomeRegistry incomeRegistry = new IncomeRegistry(1,
                1,
                1,645.45,
                Instant.now(),
                "Income tag",
                null,
                null
        );
        IncomeRegistry incomeRegistry2 = new IncomeRegistry(1,
                1,
                1,645.45,
                Instant.now(),
                "Income tag",
                null,
                null
        );

        incomeRegistryList.add(incomeRegistry);
        incomeRegistryList.add(incomeRegistry2);

        //when
        List<IncomeRegistryDto> incomeRegistryDtoList = mapper.toIncomeRegistryDtoList(incomeRegistryList);

        //then
        assertThat(incomeRegistryDtoList).isNotNull();
        assertThat(incomeRegistryDtoList.size()).isEqualTo(2);
    }

    @Test
    void shouldMapToCreateIncomeRegistryDto() {
        //given
        IncomeRegistryMapper mapper = Mappers.getMapper(IncomeRegistryMapper.class);
        IncomeRegistry incomeRegistry = new IncomeRegistry(1,
                1,
                1,645.45,
                Instant.now(),
                "Income tag",
                null,
                null
        );

        //when
        CreateIncomeRegistryDto createIncomeRegistryDto = mapper.toCreateIncomeRegistryDto(incomeRegistry);

        //then
        assertThat(createIncomeRegistryDto).isNotNull();
        assertThat(createIncomeRegistryDto.getTag()).isEqualTo("Income tag");
    }

    @Test
    void shouldMapToIncomeRegistry() {
        //given
        IncomeRegistryMapper mapper = Mappers.getMapper(IncomeRegistryMapper.class);
        CreateIncomeRegistryDto createIncomeRegistryDto = new CreateIncomeRegistryDto(1,
                1,
                1,
                645.45,
                "Income tag"
        );

        //when
        IncomeRegistry incomeRegistry = mapper.toIncomeRegistry(createIncomeRegistryDto);

        //then
        assertThat(incomeRegistry).isNotNull();
        assertThat(incomeRegistry.getTime()).isNotNull();
    }
}