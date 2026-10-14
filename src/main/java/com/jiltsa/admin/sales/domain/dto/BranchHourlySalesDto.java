package com.jiltsa.admin.sales.domain.dto;

import java.util.List;

/** Averages are over {@code daysWithSales}, so days without uploaded sales don't count. */
public record BranchHourlySalesDto(Integer branchId, String branchName, int daysWithSales, List<HourlySalesDto> hours) {
}
