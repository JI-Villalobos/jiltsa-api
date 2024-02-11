package com.jiltsa.admin.billing.persistence.repository;

import com.jiltsa.admin.billing.domain.dto.ProviderDto;
import com.jiltsa.admin.billing.domain.repository.ProviderDRepository;
import com.jiltsa.admin.billing.persistence.entity.Provider;
import com.jiltsa.admin.billing.persistence.mapper.ProviderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProviderRepositoryImplementation implements ProviderDRepository {
    private final ProviderRepository repository;
    private final ProviderMapper mapper;
    @Override
    public List<ProviderDto> getProviders() {
        return mapper.toProviderDtoList(repository.findAll());
    }

    @Override
    public ProviderDto createProvider(ProviderDto providerDto) {
        Provider provider = mapper.toProvider(providerDto);
        return mapper.toProviderDto(repository.save(provider));
    }
}
