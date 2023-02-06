package com.jiltsa.admin.cashproof.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class IncomeRegistryDto {
    private Integer id;
    private Integer accountingId;
    private Integer incomeTypeId;
    private Double amount;
    private Instant time;
    private String tag;
}
