package com.jiltsa.admin.billing.domain.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PaymentDto(Integer id, @NotNull LocalDateTime date, @NotNull Double amount, @NotNull String ticket) {
    public PaymentDto(LocalDateTime date, Double amount, String ticket) {
        this(null, date, amount, ticket);
    }
}
