package com.jiltsa.admin.sales.domain.dto;

import java.util.Map;

public record SalesProjectionDto(Map<String, SaleSummaryDto> totalByCategory , Map<String, Double> totalByUser, Map<String, SaleSummaryDto> totalByDescription ) {
}
