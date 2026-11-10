package com.jiltsa.admin.files.storage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.util.unit.DataSize;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.util.List;

/**
 * Object storage the files module uploads to through presigned URLs.
 * <ul>
 *   <li>{@code app.storage.bucket}: bucket name (env APP_STORAGE_BUCKET, required).</li>
 *   <li>{@code app.storage.region}: AWS region (env APP_STORAGE_REGION, default us-east-1).</li>
 *   <li>{@code app.storage.endpoint}: S3-compatible endpoint such as MinIO; blank for AWS
 *       (env APP_STORAGE_ENDPOINT). Path-style URLs are used when set.</li>
 *   <li>{@code app.storage.access-key} / {@code secret-key}: static credentials
 *       (env APP_STORAGE_ACCESS_KEY / APP_STORAGE_SECRET_KEY); when blank the AWS default
 *       chain applies (AWS_ACCESS_KEY_ID/AWS_SECRET_ACCESS_KEY, profile, IAM role).</li>
 *   <li>{@code upload-url-expiration} / {@code download-url-expiration}: how long a presigned
 *       URL stays valid (defaults 15m and 1h).</li>
 *   <li>{@code max-file-size}: largest upload accepted (default 10MB).</li>
 *   <li>{@code allowed-content-types}: MIME types accepted for upload (default images and PDF).</li>
 * </ul>
 */
@Validated
@ConfigurationProperties("app.storage")
public record StorageProperties(
        @NotBlank String bucket,
        @NotBlank @DefaultValue("us-east-1") String region,
        String endpoint,
        String accessKey,
        String secretKey,
        @DefaultValue("15m") Duration uploadUrlExpiration,
        @DefaultValue("1h") Duration downloadUrlExpiration,
        @DefaultValue("10MB") DataSize maxFileSize,
        @NotEmpty @DefaultValue({"image/jpeg", "image/png", "image/webp", "application/pdf"}) List<String> allowedContentTypes
) {
    public boolean hasEndpoint() {
        return endpoint != null && !endpoint.isBlank();
    }

    public boolean hasStaticCredentials() {
        return accessKey != null && !accessKey.isBlank() && secretKey != null && !secretKey.isBlank();
    }

    public boolean allows(String contentType) {
        return allowedContentTypes.stream().anyMatch(allowed -> allowed.equalsIgnoreCase(contentType));
    }
}
