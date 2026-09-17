package com.jiltsa.admin.cashproof.domain.dto;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private String description;
    @NotNull
    private Double amount;
}
