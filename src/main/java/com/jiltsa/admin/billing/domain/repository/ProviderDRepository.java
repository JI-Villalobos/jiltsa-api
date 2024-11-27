package com.jiltsa.admin.billing.domain.repository;

import com.jiltsa.admin.billing.domain.dto.ProviderDto;

import java.util.List;
import java.util.Optional;

public interface ProviderDRepository {
    List<ProviderDto> getProviders();
    ProviderDto createProvider(ProviderDto providerDto);
    Optional<ProviderDto> getProvider(Integer providerId);
    ProviderDto updateProvider(ProviderDto providerDto);
}
