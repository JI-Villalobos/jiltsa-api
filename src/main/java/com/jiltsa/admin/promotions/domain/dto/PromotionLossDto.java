package com.jiltsa.admin.promotions.domain.dto;

import java.time.LocalDate;
import java.util.List;

/** A closed promotion within the projected range, its recorded loss and the medicines behind it. */
public record PromotionLossDto(
        Integer recordId,
        Integer promotionId,
        String promotionName,
        Integer branchId,
        LocalDate promotionEndDate,
        LocalDate recordedOn,
        Double totalLoss,
        List<PromotionItemLossDto> items
) {
}
