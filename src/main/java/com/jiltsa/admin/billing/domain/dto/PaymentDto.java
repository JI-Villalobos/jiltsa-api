package com.jiltsa.admin.billing.domain.dto;

import java.time.LocalDateTime;

public record PaymentDto(Integer id, LocalDateTime date, Double amount, String ticket) {
    public PaymentDto(LocalDateTime date, Double amount, String ticket) {
        this(null, date, amount, ticket);
    }
}
