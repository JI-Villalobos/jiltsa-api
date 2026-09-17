package com.jiltsa.admin.cashproof.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CashWithdrawalDto {
    private Integer id;
    private LocalDateTime date;
    @NotNull
    private Double amount;
    @NotNull
    private String concept;
    @NotNull
    private String sellerName;
    @NotNull
    private String branch;
}
