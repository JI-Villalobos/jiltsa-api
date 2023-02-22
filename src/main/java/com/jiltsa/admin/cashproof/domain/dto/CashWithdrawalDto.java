package com.jiltsa.admin.cashproof.domain.dto;

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
    private Double amount;
    private String concept;
    private String sellerName;
}
