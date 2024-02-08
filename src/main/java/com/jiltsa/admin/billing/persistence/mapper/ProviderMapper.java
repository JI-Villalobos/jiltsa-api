package com.jiltsa.admin.billing.persistence.mapper;

import com.jiltsa.admin.billing.domain.dto.ProviderDto;
import com.jiltsa.admin.billing.persistence.entity.Provider;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProviderMapper {
    ProviderDto toProviderDto(Provider provider);
    List<ProviderDto> toProviderDtoList(List<Provider> providers);

    @InheritInverseConfiguration
    @Mapping(target = "bills", ignore = true)
    Provider toProvider(ProviderDto providerDto);
}
