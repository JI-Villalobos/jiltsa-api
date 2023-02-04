package com.jiltsa.admin.cashproof.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class ExpenseRegistryDto {
    private Integer id;
    private Integer expenseTypeId;
    private Instant time;
    private String description;
    private Double amount;
}
