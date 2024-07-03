package com.jiltsa.admin.cashproof.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PartialDto {
    private Integer id;
    private Integer creditSaleId;
    private LocalDateTime paymentDate;
    private Double amount;

    public PartialDto(Integer creditSaleId, LocalDateTime paymentDate, Double amount) {
        this.creditSaleId = creditSaleId;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }
}
