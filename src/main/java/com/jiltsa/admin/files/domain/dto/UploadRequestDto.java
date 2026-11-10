package com.jiltsa.admin.files.domain.dto;

import com.jiltsa.admin.files.domain.FileOwnerType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/** What the client is about to upload and which entity it belongs to. */
public record UploadRequestDto(
        @NotBlank @Size(max = 255) String fileName,
        @NotBlank @Size(max = 255) String contentType,
        @NotNull @Positive Long sizeBytes,
        @NotNull FileOwnerType ownerType,
        @NotNull Long ownerId
) {
}
