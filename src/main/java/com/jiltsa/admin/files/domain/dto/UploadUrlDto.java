package com.jiltsa.admin.files.domain.dto;

import java.time.Instant;
import java.util.Map;

/**
 * Where to PUT the bytes. Send the request with exactly {@code headers}; afterwards call
 * POST /files/{fileId}/confirm so the record moves from PENDING to UPLOADED.
 */
public record UploadUrlDto(
        Long fileId,
        String objectKey,
        String method,
        String uploadUrl,
        Map<String, String> headers,
        Instant expiresAt
) {
}
