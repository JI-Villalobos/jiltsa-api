package com.jiltsa.admin.cashproof.persistence.mapper;

import com.jiltsa.admin.cashproof.domain.dto.IncomeTypeDto;
import com.jiltsa.admin.cashproof.persistence.entity.IncomeType;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IncomeTypeMapper {
    IncomeTypeDto toIncomeTypeDto(IncomeType incomeType);
    List<IncomeTypeDto> incomeTypeDtoList(List<IncomeType> incomeTypeList);

    @InheritInverseConfiguration
    @Mapping(target = "incomeRegistries", ignore = true)
    IncomeType toIncomeType(IncomeTypeDto incomeTypeDto);
}
