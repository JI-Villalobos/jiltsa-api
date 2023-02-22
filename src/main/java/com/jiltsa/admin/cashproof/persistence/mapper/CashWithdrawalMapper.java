package com.jiltsa.admin.cashproof.persistence.mapper;

import com.jiltsa.admin.cashproof.domain.dto.CashWithdrawalDto;
import com.jiltsa.admin.cashproof.domain.dto.CreateCashWithdrawalDto;
import com.jiltsa.admin.cashproof.persistence.entity.CashWithdrawal;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


import java.util.List;

@Mapper(componentModel = "spring")
public interface CashWithdrawalMapper {
    CashWithdrawalDto toCashWithdrawalDto(CashWithdrawal cashWithdrawal);
    List<CashWithdrawalDto> toCashWithdrawalDtoList(List<CashWithdrawal> cashWithdrawalList);
    CreateCashWithdrawalDto toCreateCashWithdrawalDto(CashWithdrawal cashWithdrawal);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target="date", ignore = true)
    })
    CashWithdrawal toCashWithdrawal(CreateCashWithdrawalDto createCashWithdrawalDto);
}
