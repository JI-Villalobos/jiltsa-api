package com.jiltsa.admin.sales.domain.dto;

import java.util.Map;

public record SalesProjectionDto(Map<String, Double> totalByCategory , Map<String, Double> totalByUser, Map<String, Double> totalByDescription ) {
}
