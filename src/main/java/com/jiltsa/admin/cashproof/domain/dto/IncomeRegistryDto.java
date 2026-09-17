package com.jiltsa.admin.cashproof.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class IncomeRegistryDto {
    private Integer id;
    @NotNull
    private Integer accountingId;
    @NotNull
    private Integer incomeTypeId;
    @NotNull
    private Double amount;
    private Instant time;
    @NotNull
    private String tag;
}
