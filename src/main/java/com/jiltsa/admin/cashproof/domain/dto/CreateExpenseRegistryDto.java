package com.jiltsa.admin.cashproof.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class CreateExpenseRegistryDto {
    private Integer id;
    private Integer expenseTypeId;
    private String description;
    private Double amount;
}
