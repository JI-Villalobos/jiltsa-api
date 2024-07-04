package com.jiltsa.admin.cashproof.domain.dto;

import java.time.LocalDateTime;

public record PartialDto(Integer id, Integer creditSaleId, LocalDateTime paymentDate, Double amount) {
    public PartialDto(Integer creditSaleId, LocalDateTime paymentDate, Double amount) {
        this(null, creditSaleId, paymentDate, amount);
    }
}
