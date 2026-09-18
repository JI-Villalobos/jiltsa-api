package com.jiltsa.admin.billing.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record PaymentDto(Integer id, @NotNull LocalDateTime date, @NotNull @Positive Double amount, @NotBlank String ticket) {
    public PaymentDto(LocalDateTime date, Double amount, String ticket) {
        this(null, date, amount, ticket);
    }
}
