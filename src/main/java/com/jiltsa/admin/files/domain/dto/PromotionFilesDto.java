package com.jiltsa.admin.files.domain.dto;

import java.util.List;

/** Everything uploaded for a promotion, medicine by medicine, with download URLs ready to use. */
public record PromotionFilesDto(Integer promotionId, String promotionName, int files, List<PromotionItemFilesDto> items) {
}
