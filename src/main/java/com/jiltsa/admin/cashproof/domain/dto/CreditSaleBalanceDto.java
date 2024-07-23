package com.jiltsa.admin.cashproof.domain.dto;

public record CreditSaleBalanceDto(
        Integer numberOfPayments,
        Double totalPayments,
        Double outstandingBalance) {
}
