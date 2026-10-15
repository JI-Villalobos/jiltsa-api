package com.jiltsa.admin.sales.domain.dto;

import java.time.LocalDate;
import java.util.List;

public record HourlySalesProjectionDto(LocalDate initialDate, LocalDate finalDate, List<BranchHourlySalesDto> branches) {
}
