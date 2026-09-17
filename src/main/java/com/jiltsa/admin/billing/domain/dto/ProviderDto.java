package com.jiltsa.admin.billing.domain.dto;

import jakarta.validation.constraints.NotNull;
public record ProviderDto(Integer id, @NotNull String name, @NotNull String rfc) {
    public ProviderDto(String name, String rfc) {
        this(null, name, rfc);
    }
}
