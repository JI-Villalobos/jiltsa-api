package com.jiltsa.admin.cashproof.persistence.mapper;

import com.jiltsa.admin.cashproof.domain.dto.CashSortingDto;
import com.jiltsa.admin.cashproof.persistence.entity.CashSorting;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CashSortingMapper {
    CashSortingDto toCashSortingDto(CashSorting cashSorting);

    @InheritInverseConfiguration
    CashSorting toCashSorting(CashSortingDto cashSortingDto);
}
