package com.jiltsa.admin.files.domain.dto;

import java.util.List;

/** A promotion item (medicine) and the files uploaded for it. */
public record PromotionItemFilesDto(Long itemId, String key, String description, List<StoredFileDto> files) {
}
