package com.jiltsa.admin.billing.domain.service;

import com.jiltsa.admin.billing.domain.dto.ProviderDto;
import com.jiltsa.admin.billing.domain.repository.ProviderDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProviderService {
    private final ProviderDRepository providerDRepository;

    public List<ProviderDto> getAllProviders(){
        return providerDRepository.getProviders();
    }

    public ProviderDto saveProvider(ProviderDto providerDto){
        return providerDRepository.createProvider(providerDto);
    }
}
