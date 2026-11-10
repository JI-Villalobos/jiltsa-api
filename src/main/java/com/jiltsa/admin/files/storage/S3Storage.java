package com.jiltsa.admin.files.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Instant;
import java.util.Optional;

/**
 * The bucket as the files module sees it. Presigned URLs are signed locally, so issuing one
 * never calls S3; head and delete do.
 */
@Component
@RequiredArgsConstructor
public class S3Storage {
    private final S3Client client;
    private final S3Presigner presigner;
    private final StorageProperties properties;

    public record PresignedUrl(String url, Instant expiresAt) {}

    public record ObjectInfo(long sizeBytes, String etag) {}

    public String bucket() {
        return properties.bucket();
    }

    /** PUT URL bound to this content type and length: the client must send exactly those headers. */
    public PresignedUrl presignUpload(String key, String contentType, long sizeBytes) {
        PutObjectRequest put = PutObjectRequest.builder()
                .bucket(properties.bucket()).key(key).contentType(contentType).contentLength(sizeBytes).build();
        PresignedPutObjectRequest presigned = presigner.presignPutObject(PutObjectPresignRequest.builder()
                .signatureDuration(properties.uploadUrlExpiration()).putObjectRequest(put).build());
        return new PresignedUrl(presigned.url().toExternalForm(), presigned.expiration());
    }

    /** GET URL that serves the object inline under its original file name. */
    public PresignedUrl presignDownload(String key, String fileName, String contentType) {
        GetObjectRequest get = GetObjectRequest.builder()
                .bucket(properties.bucket()).key(key)
                .responseContentType(contentType)
                .responseContentDisposition("inline; filename=\"" + fileName.replaceAll("[\"\\\\\\p{Cntrl}]", "_") + "\"")
                .build();
        PresignedGetObjectRequest presigned = presigner.presignGetObject(GetObjectPresignRequest.builder()
                .signatureDuration(properties.downloadUrlExpiration()).getObjectRequest(get).build());
        return new PresignedUrl(presigned.url().toExternalForm(), presigned.expiration());
    }

    /** Empty when no object sits at the key, i.e. the client never completed the upload. */
    public Optional<ObjectInfo> head(String key) {
        try {
            HeadObjectResponse response = client.headObject(HeadObjectRequest.builder().bucket(properties.bucket()).key(key).build());
            return Optional.of(new ObjectInfo(response.contentLength(), response.eTag()));
        } catch (NoSuchKeyException e) {
            return Optional.empty();
        } catch (S3Exception e) {
            if (e.statusCode() == 404) return Optional.empty();
            throw e;
        }
    }

    /** Deleting a key that holds no object is not an error for S3 either. */
    public void delete(String key) {
        client.deleteObject(DeleteObjectRequest.builder().bucket(properties.bucket()).key(key).build());
    }
}
