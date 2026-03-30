package com.jiltsa.admin.operativity.domain.dto;

public record BreakEvenPointDto(
            Integer id, Double paysheet, Double rent,
            Double electricityService, Double internet, Double otherServices,
            Double gasolineAndTransport, Double adminPayment, Double operatingCost,
            Double fixedExpenses, Double pe
            ) {
}
