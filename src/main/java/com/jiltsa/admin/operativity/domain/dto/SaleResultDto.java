package com.jiltsa.admin.operativity.domain.dto;

import java.time.LocalDateTime;

public record SaleResultDto(
        Integer id, Double pharmacyAmount, Double servicesAmount,
        Integer weekNumber, Integer year, LocalDateTime initialDate, LocalDateTime finalDate, Integer branchId
) {
}
