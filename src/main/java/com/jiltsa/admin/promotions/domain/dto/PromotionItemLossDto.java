package com.jiltsa.admin.promotions.domain.dto;

import java.time.LocalDate;

/** One medicine of a closed promotion and what it cost: quantity x purchasePrice. */
public record PromotionItemLossDto(
        Long itemId,
        String key,
        String description,
        Integer quantity,
        Double normalPrice,
        Double promotionPrice,
        Double purchasePrice,
        LocalDate expirationDate,
        Double loss
) {
}
