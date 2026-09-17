package com.jiltsa.admin.billing.domain.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record BillDto(Integer id, @NotNull LocalDateTime date, @NotNull String invoice,
                      @NotNull String branch, @NotNull Integer branchId, @NotNull Double amount,
                      LocalDateTime limitPaymentDate, Boolean isPaid,
                      String paymentTicket, @NotNull Integer providerId,
                      Boolean isActive, LocalDateTime receptionDate) {
    public BillDto(LocalDateTime date, String invoice, String branch, Integer branchId, Double amount) {
        this(null, date, invoice, branch, branchId, amount,
                null, null, null, null, null, null);
    }

    public BillDto(LocalDateTime date, String invoice, String branch, Integer branchId, Double amount,
                   Integer providerId) {
        this(null, date, invoice, branch, branchId, amount, null, null, null,
                providerId, null, null);
    }

    public BillDto(LocalDateTime date, String invoice, String branch, Integer branchId, Double amount, LocalDateTime limitPaymentDate, Integer providerId) {
        this(null, date, invoice, branch, branchId, amount, limitPaymentDate, null, null, providerId, null, null);
    }
}
