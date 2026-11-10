package com.jiltsa.admin.files.domain.dto;

import com.jiltsa.admin.files.domain.FileDocumentType;
import com.jiltsa.admin.files.domain.FileOwnerType;
import com.jiltsa.admin.files.domain.FileStatus;

import java.time.Instant;
import java.time.LocalDateTime;

/** A file record; {@code downloadUrl} is a presigned GET, present only once the file is UPLOADED. */
public record StoredFileDto(
        Long id,
        String fileName,
        String contentType,
        Long sizeBytes,
        FileStatus status,
        FileOwnerType ownerType,
        Long ownerId,
        FileDocumentType documentType,
        String uploadedBy,
        LocalDateTime createdAt,
        LocalDateTime uploadedAt,
        String downloadUrl,
        Instant downloadUrlExpiresAt
) {
}
