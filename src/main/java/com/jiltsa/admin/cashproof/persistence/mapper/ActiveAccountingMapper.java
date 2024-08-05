package com.jiltsa.admin.cashproof.persistence.mapper;

import com.jiltsa.admin.cashproof.domain.dto.ActiveAccountingDto;
import com.jiltsa.admin.cashproof.persistence.entity.ActiveAccounting;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActiveAccountingMapper {
    ActiveAccountingDto toActiveAccountingDto(ActiveAccounting activeAccounting);

    @InheritInverseConfiguration
    ActiveAccounting toActiveAccounting(ActiveAccountingDto activeAccountingDto);
}
