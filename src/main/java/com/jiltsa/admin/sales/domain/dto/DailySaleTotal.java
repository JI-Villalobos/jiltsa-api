package com.jiltsa.admin.sales.domain.dto;

import java.time.LocalDate;

/** Sales of one branch on one day, as aggregated by the database. */
public record DailySaleTotal(Integer branchId, LocalDate date, Double total, Double approximatedUtility) {
}
