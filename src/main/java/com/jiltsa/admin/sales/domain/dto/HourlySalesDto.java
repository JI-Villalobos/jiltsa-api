package com.jiltsa.admin.sales.domain.dto;

/** Average sales between {@code hour}:00 and {@code hour + 1}:00. */
public record HourlySalesDto(int hour, double averageSales) {
}
