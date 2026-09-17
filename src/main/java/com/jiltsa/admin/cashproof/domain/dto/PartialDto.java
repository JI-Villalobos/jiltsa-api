package com.jiltsa.admin.cashproof.domain.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PartialDto(Integer id, @NotNull Integer creditSaleId, @NotNull LocalDateTime paymentDate, @NotNull Double amount) {
    public PartialDto(Integer creditSaleId, LocalDateTime paymentDate, Double amount) {
        this(null, creditSaleId, paymentDate, amount);
    }
}
