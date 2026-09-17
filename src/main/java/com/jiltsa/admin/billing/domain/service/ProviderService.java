package com.jiltsa.admin.billing.domain.service;

import com.jiltsa.admin.billing.domain.dto.ProviderDto;
import com.jiltsa.admin.billing.persistence.entity.Provider;
import com.jiltsa.admin.billing.persistence.mapper.ProviderMapper;
import com.jiltsa.admin.billing.persistence.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProviderService {
    private final ProviderRepository repository;
    private final ProviderMapper mapper;

    public List<ProviderDto> getAllProviders() {
        return mapper.toProviderDtoList(repository.findAll());
    }

    @Transactional
    public ProviderDto saveProvider(ProviderDto providerDto) {
        Provider provider = mapper.toProvider(providerDto);
        return mapper.toProviderDto(repository.save(provider));
    }

    public Optional<ProviderDto> getProvider(Integer providerId) {
        return repository.findById(providerId).map(mapper::toProviderDto);
    }

    @Transactional
    public ProviderDto updateProvider(ProviderDto providerDto) {
        Provider provider = mapper.toProvider(providerDto);
        return mapper.toProviderDto(repository.save(provider));
    }
}
