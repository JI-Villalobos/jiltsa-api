package com.jiltsa.admin.sales.persistence.mapper;

import com.jiltsa.admin.sales.domain.dto.SaleDto;
import com.jiltsa.admin.sales.persistence.entity.Sale;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SaleMapper {
    SaleDto toSaleDto(Sale sale);
    List<SaleDto> toSaleDtoList(List<Sale> sales);

    @InheritInverseConfiguration
    Sale toSale(SaleDto saleDto);
}
