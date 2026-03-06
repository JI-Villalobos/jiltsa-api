package com.jiltsa.admin.sales.domain;

import jakarta.persistence.Column;

import java.time.LocalDateTime;

public record SaleDto(
         Long id,
         Integer branchId,
         String key,
         String description,
         Long ticket,
         String category,
         Integer quantity,
         Double price,
         Double purchasePrice,
         Double approximatedUtility,
         Double total,
         LocalDateTime timestamp,
         String user
) {
}
