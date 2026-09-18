package com.jiltsa.admin.billing.domain.dto;

import jakarta.validation.constraints.NotBlank;
public record ProviderDto(Integer id, @NotBlank String name, @NotBlank String rfc) {
    public ProviderDto(String name, String rfc) {
        this(null, name, rfc);
    }
}
