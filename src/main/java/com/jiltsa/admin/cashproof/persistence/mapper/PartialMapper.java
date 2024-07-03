package com.jiltsa.admin.cashproof.persistence.mapper;

import com.jiltsa.admin.cashproof.domain.dto.PartialDto;
import com.jiltsa.admin.cashproof.persistence.entity.Partial;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PartialMapper {
    PartialDto toPartialDto(Partial partial);

    @InheritInverseConfiguration
    @Mapping(target = "creditSale", ignore = true)
    Partial toPartial(PartialDto partialDto);
}
