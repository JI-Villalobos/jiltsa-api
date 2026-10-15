package com.jiltsa.admin.sales.domain.dto;

import java.time.LocalDate;
import java.util.List;

public record DailySalesProjectionDto(LocalDate initialDate, LocalDate finalDate, List<BranchDailySalesDto> branches) {
}
