package com.jiltsa.admin.sales.domain.dto;

import java.time.LocalDate;

/** A day with no uploaded sales is {@code pending}, with zero totals. */
public record DailySalesDto(LocalDate date, double total, double approximatedUtility, boolean pending) {
}
