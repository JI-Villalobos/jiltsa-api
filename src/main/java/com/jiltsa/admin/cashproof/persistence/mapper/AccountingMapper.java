package com.jiltsa.admin.cashproof.persistence.mapper;

import com.jiltsa.admin.cashproof.domain.dto.AccountingDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateAccountingDto;
import com.jiltsa.admin.cashproof.domain.dto.CustomAccountingDto;
import com.jiltsa.admin.cashproof.persistence.entity.Accounting;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountingMapper {
    AccountingDto toAccountingDto(Accounting accounting);
    default Page<AccountingDto> toAccountingDtoPage(Page<Accounting> accounts){
        return accounts.map(this::toAccountingDto);
    }
    List<AccountingDto> toAccountingDtoList(List<Accounting> accountingList);
    CreateAccountingDto toCreateAccountingDto(Accounting accounting);
    CustomAccountingDto toCustomAccountingDto(Accounting accounting);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "branch", ignore = true),
            @Mapping(target = "seller", ignore = true),
            @Mapping(target = "incomeRegistries", ignore = true),
            @Mapping(target = "expenseRegistries", ignore = true)
    })
    Accounting toAccounting(CreateAccountingDto createAccountingDto);
    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "branch", ignore = true),
            @Mapping(target = "seller", ignore = true),
            @Mapping(target = "incomeRegistries", ignore = true),
            @Mapping(target = "expenseRegistries", ignore = true)
    })
    Accounting toAccounting(CustomAccountingDto customAccountingDto);
}
