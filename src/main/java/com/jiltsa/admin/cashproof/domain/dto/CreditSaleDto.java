package com.jiltsa.admin.cashproof.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.List;

public record CreditSaleDto(Integer id, @NotBlank String concept, @NotBlank String client, @NotNull LocalDateTime date,
                            @NotNull @Positive Double amount, @NotNull Integer branchId, Boolean isPaid, List<PartialDto> partials) {
    public CreditSaleDto(String concept, String client, LocalDateTime date, Double amount, Integer branchId) {
        this(null, concept, client, date, amount, branchId, null, null);
    }

    public CreditSaleDto(Integer id, String concept, String client, LocalDateTime date, Double amount, Integer branchId, Boolean isPaid) {
        this(id, concept, client, date, amount, branchId, isPaid, null);
    }

}
