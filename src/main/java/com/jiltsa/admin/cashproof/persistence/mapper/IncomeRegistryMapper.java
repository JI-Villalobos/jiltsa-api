package com.jiltsa.admin.cashproof.persistence.mapper;

import com.jiltsa.admin.cashproof.domain.dto.CreateIncomeRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.IncomeRegistryDto;
import com.jiltsa.admin.cashproof.persistence.entity.IncomeRegistry;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IncomeRegistryMapper {
    IncomeRegistryDto toIncomeRegistryDto(IncomeRegistry incomeRegistry);
    List<IncomeRegistryDto> toIncomeRegistryDtoList(List<IncomeRegistry> incomeRegistryList);
    CreateIncomeRegistryDto toCreateIncomeRegistryDto(IncomeRegistry incomeRegistry);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "time", ignore = true),
            @Mapping(target = "accounting", ignore = true),
            @Mapping(target = "incomeType", ignore = true),
            @Mapping(target = "modifiedDate", ignore = true)
    })
    IncomeRegistry toIncomeRegistry(CreateIncomeRegistryDto createIncomeRegistryDto);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "time", ignore = true),
            @Mapping(target = "accounting", ignore = true),
            @Mapping(target = "incomeType", ignore = true),
            @Mapping(target = "modifiedDate", ignore = true)
    })
    List<IncomeRegistry> toIncomeRegistryList(List<CreateIncomeRegistryDto> incomes);
}
