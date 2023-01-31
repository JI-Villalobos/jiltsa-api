package com.jiltsa.admin.cashproof.persistence.mapper;

import com.jiltsa.admin.cashproof.domain.dto.AccountingDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateAccountingDto;
import com.jiltsa.admin.cashproof.persistence.entity.Accounting;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AccountingMapper {
    AccountingDto toAccountingDto(Accounting accounting);
    CreateAccountingDto toCreateAccountingDto(Accounting accounting);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "branch", ignore = true),
            @Mapping(target = "seller", ignore = true),
            @Mapping(target = "incomeRegistries", ignore = true),
            @Mapping(target = "expenseRegistries", ignore = true),
            @Mapping(target = "date", ignore = true)
    })
    Accounting toAccounting(CreateAccountingDto createAccountingDto);
}
