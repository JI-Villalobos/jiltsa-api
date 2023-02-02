package com.jiltsa.admin.cashproof.persistence.mapper;

import com.jiltsa.admin.cashproof.domain.dto.ExpenseTypeDto;
import com.jiltsa.admin.cashproof.persistence.entity.ExpenseType;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseTypeMapper {
    ExpenseTypeDto toExpenseTypeDto(ExpenseType expenseType);
    List<ExpenseTypeDto> toExpenseTypeDtoList(List<ExpenseType> expenseTypeList);
    @InheritInverseConfiguration
    @Mapping(target = "expenseRegistries", ignore = true)
    ExpenseType toExpenseType(ExpenseTypeDto expenseTypeDto);
}
