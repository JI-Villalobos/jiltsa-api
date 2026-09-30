package com.jiltsa.admin.billing.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.billing.domain.dto.ProviderDto;
import com.jiltsa.admin.billing.domain.service.ProviderService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/providers")
@RequiredArgsConstructor
public class ProviderController {
    private final ProviderService service;

    @Operation(summary = "List all providers")
    @GetMapping
    public List<ProviderDto> getProviders(){
        return service.getAllProviders();
    }

    @Operation(summary = "Create a provider")
    @PostMapping
    public ProviderDto createProvider(@Valid @RequestBody ProviderDto providerDto){
        return service.saveProvider(providerDto);
    }

    @Operation(summary = "Get a provider")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No provider with that id")
    @GetMapping("/{providerId}")
    public ProviderDto getProvider(@Parameter(description = "Provider id") @PathVariable("providerId") Integer providerId){
        return service.getProvider(providerId)
                .orElseThrow(() -> new ResourceNotFoundException("Provider", providerId));
    }

    @Operation(summary = "Update a provider")
    @PutMapping
    public ProviderDto updateProvider(@Valid @RequestBody ProviderDto providerDto){
        return service.updateProvider(providerDto);
    }
}
