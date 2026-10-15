package com.jiltsa.admin.cashproof.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @NotNull @Positive
    private Double amount;
    private Instant time;
    @NotBlank
    private String tag;
}
