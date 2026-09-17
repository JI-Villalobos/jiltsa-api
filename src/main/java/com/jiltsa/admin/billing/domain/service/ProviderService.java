package com.jiltsa.admin.billing.domain.service;

import com.jiltsa.admin.billing.domain.dto.ProviderDto;
import com.jiltsa.admin.billing.domain.repository.ProviderDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProviderService {
    private final ProviderDRepository providerDRepository;

    public List<ProviderDto> getAllProviders(){
        return providerDRepository.getProviders();
    }

    @Transactional
    public ProviderDto saveProvider(ProviderDto providerDto){
        return providerDRepository.createProvider(providerDto);
    }

    public Optional<ProviderDto> getProvider(Integer providerId){
        return providerDRepository.getProvider(providerId);
    }

    @Transactional
    public ProviderDto updateProvider(ProviderDto providerDto) {
        return providerDRepository.updateProvider(providerDto);
    }
}
