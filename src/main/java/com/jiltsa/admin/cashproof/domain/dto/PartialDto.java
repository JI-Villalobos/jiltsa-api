package com.jiltsa.admin.cashproof.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record PartialDto(Integer id, @NotNull Integer creditSaleId, @NotNull LocalDateTime paymentDate, @NotNull @Positive Double amount) {
    public PartialDto(Integer creditSaleId, LocalDateTime paymentDate, Double amount) {
        this(null, creditSaleId, paymentDate, amount);
    }
}
