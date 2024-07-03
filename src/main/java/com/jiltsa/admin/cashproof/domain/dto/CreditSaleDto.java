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
    private Boolean isPaid;
    List<PartialDto> partials;

    public CreditSaleDto(String concept, LocalDateTime date, Double amount) {
        this.concept = concept;
        this.date = date;
        this.amount = amount;
    }

    public CreditSaleDto(Integer id, String concept, LocalDateTime date, Double amount, Boolean isPaid) {
        this.id = id;
        this.concept = concept;
        this.date = date;
        this.amount = amount;
        this.isPaid = isPaid;
    }


}
