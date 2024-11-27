package com.jiltsa.admin.billing.controller;

import com.jiltsa.admin.billing.domain.dto.ProviderDto;
import com.jiltsa.admin.billing.domain.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("jiltsa/api/v1/providers")
@RequiredArgsConstructor
public class ProviderController {
    private final ProviderService service;

    @GetMapping
    public List<ProviderDto> getProviders(){
        return service.getAllProviders();
    }

    @PostMapping
    public ProviderDto createProvider(@RequestBody ProviderDto providerDto){
        return service.saveProvider(providerDto);
    }

    @GetMapping("/{providerId}")
    public Optional<ProviderDto> getProvider(@PathVariable("providerId") Integer providerId){
        return service.getProvider(providerId);
    }

    @PutMapping
    public ProviderDto updateProvider(@RequestBody ProviderDto providerDto){
        return service.updateProvider(providerDto);
    }
}
