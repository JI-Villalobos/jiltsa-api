package com.jiltsa.admin.cashproof.domain.dto;

public record ActiveAccountingDto(
        Integer id,
        Integer accountingId,
        Boolean isActive,
        Integer branchId,
        Integer sellerId
) {
    public ActiveAccountingDto(Integer accountingId, Boolean isActive, Integer branchId, Integer sellerId) {
        this(0, accountingId, isActive, branchId, sellerId);
    }
}
