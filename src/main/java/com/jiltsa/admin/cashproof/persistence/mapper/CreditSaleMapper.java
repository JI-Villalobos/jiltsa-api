package com.jiltsa.admin.cashproof.persistence.mapper;

import com.jiltsa.admin.cashproof.domain.dto.CreditSaleDto;
import com.jiltsa.admin.cashproof.persistence.entity.CreditSale;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CreditSaleMapper {
    CreditSaleDto toCreditSaleDto(CreditSale creditSale);
    List<CreditSaleDto> toCreditSaleDtoList(List<CreditSale> creditSales);

    @InheritInverseConfiguration
    @Mapping(target = "partials", ignore = true)
    CreditSale toCreditSale(CreditSaleDto creditSaleDto);
}
