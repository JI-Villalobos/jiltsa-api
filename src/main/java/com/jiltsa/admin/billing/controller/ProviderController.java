package com.jiltsa.admin.billing.controller;

import com.jiltsa.admin.billing.domain.dto.ProviderDto;
import com.jiltsa.admin.billing.domain.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}
