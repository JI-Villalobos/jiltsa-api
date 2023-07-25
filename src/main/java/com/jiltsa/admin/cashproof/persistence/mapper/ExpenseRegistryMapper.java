package com.jiltsa.admin.cashproof.persistence.mapper;

import com.jiltsa.admin.cashproof.domain.dto.CreateAccountingDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateExpenseRegistryDto;
import com.jiltsa.admin.cashproof.domain.dto.ExpenseRegistryDto;
import com.jiltsa.admin.cashproof.persistence.entity.ExpenseRegistry;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseRegistryMapper {
    ExpenseRegistryDto toExpenseRegistryDto(ExpenseRegistry expenseRegistry);
    List<ExpenseRegistryDto> toExpenseRegistryDtoList(List<ExpenseRegistry> expenseRegistryList);
    CreateExpenseRegistryDto toCreateExpenseRegistryDto(ExpenseRegistry expenseRegistry);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "accounting", ignore = true),
            @Mapping(target = "expenseType", ignore = true),
            @Mapping(target = "time", ignore = true),
            @Mapping(target = "modifiedDate", ignore = true)
    })
    ExpenseRegistry toExpenseRegistry(CreateExpenseRegistryDto createExpenseRegistryDto);
}
