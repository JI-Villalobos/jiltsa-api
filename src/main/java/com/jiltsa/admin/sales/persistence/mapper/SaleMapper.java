package com.jiltsa.admin.sales.persistence.mapper;

import com.jiltsa.admin.sales.domain.dto.CreateSaleDto;
import com.jiltsa.admin.sales.domain.dto.SaleDto;
import com.jiltsa.admin.sales.persistence.entity.Sale;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SaleMapper {
    SaleDto toSaleDto(Sale sale);
    List<SaleDto> toSaleDtoList(List<Sale> sales);

    @InheritInverseConfiguration
    Sale toSale(SaleDto saleDto);

    @Mapping(target = "id", ignore = true)
    Sale toSale(CreateSaleDto createSaleDto);
    List<Sale> toSaleList(List<CreateSaleDto> createSaleDtos);
}
