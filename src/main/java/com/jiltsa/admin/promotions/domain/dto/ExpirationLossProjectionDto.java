package com.jiltsa.admin.promotions.domain.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * Losses recorded within a date range. {@code branchId} is null when the projection covers every
 * branch. {@code totalLoss} is the sum of the records; each record breaks down into its medicines.
 */
public record ExpirationLossProjectionDto(
        Integer branchId,
        LocalDate initialDate,
        LocalDate finalDate,
        Double totalLoss,
        int promotions,
        List<PromotionLossDto> promotionLosses
) {
}
