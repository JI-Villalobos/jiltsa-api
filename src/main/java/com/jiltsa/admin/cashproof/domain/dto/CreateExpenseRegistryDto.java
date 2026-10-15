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
public class CreateExpenseRegistryDto {
    private Integer id;
    @NotNull
    private Integer accountingId;
    @NotNull
    private Integer expenseTypeId;
    @NotBlank
    private String description;
    @NotNull @Positive
    private Double amount;
}
