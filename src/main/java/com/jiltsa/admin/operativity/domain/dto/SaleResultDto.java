package com.jiltsa.admin.operativity.domain.dto;

import java.time.LocalDateTime;

public record SaleResultDto(
        Integer id, Double pharmacyAmount, Double servicesAmount,
        Integer weekNumber, LocalDateTime initialDate, LocalDateTime finalDate, Integer branchId
) {
}
