package com.jiltsa.admin.cashproof.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CreditSaleDto {
    private Integer id;
    private String concept;
    private LocalDateTime date;
    private Double amount;
    private Integer branchId;
    private Boolean isPaid;
    List<PartialDto> partials;

    public CreditSaleDto(String concept, LocalDateTime date, Double amount, Integer branchId) {
        this.concept = concept;
        this.date = date;
        this.amount = amount;
        this.branchId = branchId;
    }

    public CreditSaleDto(Integer id, String concept, LocalDateTime date, Double amount, Integer branchId, Boolean isPaid) {
        this.id = id;
        this.concept = concept;
        this.date = date;
        this.amount = amount;
        this.branchId = branchId;
        this.isPaid = isPaid;
    }
}
