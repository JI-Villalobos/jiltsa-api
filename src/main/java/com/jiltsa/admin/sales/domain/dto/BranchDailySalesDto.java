package com.jiltsa.admin.sales.domain.dto;

import java.util.List;

/** {@code averageDailySales} averages only the days that have sales, so pending uploads don't drag it down. */
public record BranchDailySalesDto(Integer branchId, String branchName, double totalSales, double averageDailySales,
                                  List<DailySalesDto> days) {
}
