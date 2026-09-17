package com.jiltsa.admin.cashproof.domain.dto;

public record ExpenseReportDto(String type, Integer branchId, Integer expenseTypeId, Double total) {
}
