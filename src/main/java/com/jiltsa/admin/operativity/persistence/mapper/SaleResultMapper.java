package com.jiltsa.admin.operativity.persistence.mapper;

import com.jiltsa.admin.operativity.domain.dto.SaleResultDto;
import com.jiltsa.admin.operativity.persistence.entity.SaleResult;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SaleResultMapper {
    SaleResultDto toSaleResultDto(SaleResult saleResult);
    List<SaleResultDto> toSaleResultDtoList(List<SaleResult> saleResults);

    @InheritInverseConfiguration
    SaleResult toSaleResult(SaleResultDto saleResultDto);
}
