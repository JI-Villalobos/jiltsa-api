package com.jiltsa.admin.operativity.persistence.mapper;

import com.jiltsa.admin.operativity.domain.dto.BreakEvenPointDto;
import com.jiltsa.admin.operativity.persistence.entity.BreakEvenPoint;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BreakEventPointMapper {
    BreakEvenPointDto toBreakEvenPointDto(BreakEvenPoint breakEvenPoint);

    @InheritInverseConfiguration
    BreakEvenPoint toBreakEvenPoint(BreakEvenPointDto breakEvenPointDto);
}
