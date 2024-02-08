package com.jiltsa.admin.billing.persistence.mapper;

import com.jiltsa.admin.billing.domain.dto.BillDto;
import com.jiltsa.admin.billing.persistence.entity.Bill;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BillMapper {
    BillDto toBillDto(Bill bill);
    List<BillDto> toBillDtoList(List<Bill> bills);

    @InheritInverseConfiguration
    @Mapping(target = "provider", ignore = true)
    Bill toBill(BillDto billDto);
}
