package com.jiltsa.admin.billing.domain.dto;

import java.time.LocalDateTime;

public record BillDto(Integer id, LocalDateTime date, String invoice,
                      String branch, Integer branchId, Double amount,
                      LocalDateTime limitPaymentDate, Boolean isPaid,
                      String paymentTicket, Integer providerId, Boolean isActive) {
    public BillDto(LocalDateTime date, String invoice, String branch, Integer branchId, Double amount) {
        this(null, date, invoice, branch, branchId, amount,
                null, null, null, null, null);
    }

    public BillDto(LocalDateTime date, String invoice, String branch, Integer branchId, Double amount,
                   Integer providerId) {
        this(null, date, invoice, branch, branchId, amount, null, null, null, providerId, null);
    }
}
