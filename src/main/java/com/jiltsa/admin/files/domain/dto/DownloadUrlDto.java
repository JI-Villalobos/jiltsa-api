package com.jiltsa.admin.files.domain.dto;

import java.time.Instant;

public record DownloadUrlDto(Long fileId, String fileName, String contentType, String downloadUrl, Instant expiresAt) {
}
