package com.jiltsa.admin.billing.persistence.mapper;

import com.jiltsa.admin.billing.domain.dto.BillDto;
import com.jiltsa.admin.billing.persistence.entity.Bill;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BillMapper {
    BillDto toBillDto(Bill bill);
    default Page<BillDto> toBillDtoPage(Page<Bill> bills){
        return bills.map(this::toBillDto);
    };
    List<BillDto> toBillDtoList(List<Bill> bills);

    @InheritInverseConfiguration
    @Mapping(target = "provider", ignore = true)
    Bill toBill(BillDto billDto);

    @InheritInverseConfiguration
    @Mapping(target = "provider", ignore = true)
    List<Bill> toBillList(List<BillDto> billDtoList);
}
