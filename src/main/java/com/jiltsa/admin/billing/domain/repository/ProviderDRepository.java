package com.jiltsa.admin.billing.domain.repository;

import com.jiltsa.admin.billing.domain.dto.ProviderDto;

import java.util.List;

public interface ProviderDRepository {
    List<ProviderDto> getProviders();
    ProviderDto createProvider(ProviderDto providerDto);
}
