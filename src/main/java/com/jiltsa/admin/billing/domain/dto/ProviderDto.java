package com.jiltsa.admin.billing.domain.dto;

public record ProviderDto(Integer id, String name, String rfc) {
    public ProviderDto(String name, String rfc) {
        this(null, name, rfc);
    }
}
