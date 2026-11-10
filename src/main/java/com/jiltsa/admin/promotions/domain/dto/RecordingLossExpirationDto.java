package com.jiltsa.admin.promotions.domain.dto;

import java.time.LocalDate;

/** Loss written off when the promotion closed; created by POST /promotions/{id}/close. */
public record RecordingLossExpirationDto(
        Integer id,
        Integer promotionId,
        Integer branchId,
        Double totalLoss,
        LocalDate creationDate
) {
}
