package com.jiltsa.admin.cashproof.domain.dto;

import jakarta.validation.constraints.NotNull;
public record ActiveAccountingDto(
        Integer id,
        @NotNull Integer accountingId,
        @NotNull Boolean isActive,
        @NotNull Integer branchId,
        @NotNull Integer sellerId
) {
    public ActiveAccountingDto(Integer accountingId, Boolean isActive, Integer branchId, Integer sellerId) {
        this(0, accountingId, isActive, branchId, sellerId);
    }
}
