package com.jiltsa.admin.billing.persistence.mapper;

import com.jiltsa.admin.billing.domain.dto.ProviderDto;
import com.jiltsa.admin.billing.persistence.entity.Provider;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

class ProviderMapperTest {

    @Test
    void shouldMaoToProviderDto() {
        //given
        ProviderMapper mapper = Mappers.getMapper(ProviderMapper.class);
        Provider provider = new Provider("Provider", "PROVIDER");

        //when
        ProviderDto providerDto = mapper.toProviderDto(provider);

        //then
        assertThat(providerDto).isNotNull();
        assertThat(providerDto).isInstanceOf(ProviderDto.class);
    }

    @Test
    void shouldMapToProviderDtoList() {
        //given
        ProviderMapper mapper = Mappers.getMapper(ProviderMapper.class);
        List<Provider> providers = new ArrayList<>();
        Provider provider = new Provider("Provider", "PROVIDER");
        Provider provider2 = new Provider("Provider2", "PROVIDER2");
        providers.add(provider);
        providers.add(provider2);

        //when
        List<ProviderDto> providerDtoList = mapper.toProviderDtoList(providers);

        //then
        assertThat(providerDtoList).isNotNull();
        assertThat(providerDtoList.size()).isEqualTo(2);
    }

    @Test
    void shouldMapToProvider() {
        //given
        ProviderMapper mapper = Mappers.getMapper(ProviderMapper.class);
        ProviderDto providerDto = new ProviderDto(1,"Provider", "PROVIDER");

        //when
        Provider provider = mapper.toProvider(providerDto);

        //then
        assertThat(provider).isNotNull();
        assertThat(provider.getName()).isEqualTo("Provider");
    }
}