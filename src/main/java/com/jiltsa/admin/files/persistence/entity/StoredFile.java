package com.jiltsa.admin.files.persistence.entity;

import com.jiltsa.admin.files.domain.FileDocumentType;
import com.jiltsa.admin.files.domain.FileOwnerType;
import com.jiltsa.admin.files.domain.FileStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/** One object in the bucket and the entity it belongs to. See V10__create_stored_files.sql. */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "stored_files")
public class StoredFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "object_key")
    @NotNull
    private String objectKey;

    @NotNull
    private String bucket;

    @Column(name = "file_name")
    @NotNull
    private String fileName;

    @Column(name = "content_type")
    @NotNull
    private String contentType;

    @Column(name = "size_bytes")
    private Long sizeBytes;

    private String etag;

    @Enumerated(EnumType.STRING)
    @NotNull
    private FileStatus status;

    @Column(name = "owner_type")
    @Enumerated(EnumType.STRING)
    @NotNull
    private FileOwnerType ownerType;

    @Column(name = "owner_id")
    @NotNull
    private Long ownerId;

    @Column(name = "document_type")
    @Enumerated(EnumType.STRING)
    private FileDocumentType documentType;

    @Column(name = "uploaded_by")
    @NotNull
    private String uploadedBy;

    @Column(name = "created_at")
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;

    public StoredFile(String objectKey, String bucket, String fileName, String contentType, Long sizeBytes,
                      FileOwnerType ownerType, Long ownerId, FileDocumentType documentType, String uploadedBy) {
        this.objectKey = objectKey;
        this.bucket = bucket;
        this.fileName = fileName;
        this.contentType = contentType;
        this.sizeBytes = sizeBytes;
        this.status = FileStatus.PENDING;
        this.ownerType = ownerType;
        this.ownerId = ownerId;
        this.documentType = documentType;
        this.uploadedBy = uploadedBy;
        this.createdAt = LocalDateTime.now();
    }

    public boolean isUploaded() {
        return status == FileStatus.UPLOADED;
    }
}
