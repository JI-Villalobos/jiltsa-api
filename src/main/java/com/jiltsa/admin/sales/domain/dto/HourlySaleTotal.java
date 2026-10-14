package com.jiltsa.admin.sales.domain.dto;

import java.time.LocalDate;

/** Sales of one branch within one hour of one day, as aggregated by the database. */
public record HourlySaleTotal(Integer branchId, LocalDate date, Integer hour, Double total) {
}
