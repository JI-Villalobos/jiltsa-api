package com.jiltsa.admin.cashproof.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

public record CreditSaleDto(Integer id, String concept, String client, LocalDateTime date,
                            Double amount, Integer branchId, Boolean isPaid, List<PartialDto> partials) {
    public CreditSaleDto(String concept, String client, LocalDateTime date, Double amount, Integer branchId) {
        this(null, concept, client, date, amount, branchId, null, null);
    }

    public CreditSaleDto(Integer id, String concept, String client, LocalDateTime date, Double amount, Integer branchId, Boolean isPaid) {
        this(id, concept, client, date, amount, branchId, isPaid, null);
    }

}
