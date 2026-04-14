package com.jiltsa.admin.sales.domain.dto;

import java.time.LocalDateTime;

public record ConsignmentSaleDto(Integer branchId, String item, Integer quantity, Double amount, LocalDateTime timestamp) {
}
