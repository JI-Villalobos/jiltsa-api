package com.jiltsa.admin.cashproof.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @NotNull @Positive
    private Double amount;
    @NotBlank
    private String concept;
    @NotBlank
    private String sellerName;
    @NotBlank
    private String branch;
}
