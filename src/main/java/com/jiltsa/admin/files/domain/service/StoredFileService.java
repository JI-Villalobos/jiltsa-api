package com.jiltsa.admin.files.domain.service;

import com.jiltsa.admin.common.exception.ConflictException;
import com.jiltsa.admin.common.exception.InvalidRequestException;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import com.jiltsa.admin.files.domain.FileDocumentType;
import com.jiltsa.admin.files.domain.FileOwnerType;
import com.jiltsa.admin.files.domain.FileStatus;
import com.jiltsa.admin.files.domain.dto.*;
import com.jiltsa.admin.files.persistence.entity.StoredFile;
import com.jiltsa.admin.files.persistence.mapper.StoredFileMapper;
import com.jiltsa.admin.files.persistence.repository.StoredFileRepository;
import com.jiltsa.admin.files.storage.S3Storage;
import com.jiltsa.admin.files.storage.StorageProperties;
import com.jiltsa.admin.promotions.domain.dto.PromotionDto;
import com.jiltsa.admin.promotions.domain.dto.PromotionItemDto;
import com.jiltsa.admin.promotions.domain.service.PromotionItemService;
import com.jiltsa.admin.promotions.domain.service.PromotionService;
import com.jiltsa.admin.seller.domain.service.SellerProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Upload flow: {@link #requestUpload} records the file as PENDING and hands out a presigned PUT;
 * the client uploads straight to the bucket; {@link #confirmUpload} checks the object is there and
 * marks the record UPLOADED. Only uploaded files are listed and get download URLs.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoredFileService {
    private static final Pattern EXTENSION = Pattern.compile("\\.([A-Za-z0-9]{1,10})$");

    private final StoredFileRepository repository;
    private final StoredFileMapper mapper;
    private final S3Storage storage;
    private final StorageProperties properties;
    private final PromotionItemService promotionItemService;
    private final PromotionService promotionService;
    private final SellerProfileService sellerProfileService;

    @Transactional
    public UploadUrlDto requestUpload(UploadRequestDto request) {
        if (!properties.allows(request.contentType())) {
            throw new InvalidRequestException("Content type not allowed: " + request.contentType()
                    + ". Allowed: " + String.join(", ", properties.allowedContentTypes()));
        }
        if (request.sizeBytes() > properties.maxFileSize().toBytes()) {
            throw new InvalidRequestException("File too large: " + request.sizeBytes() + " bytes; the limit is "
                    + properties.maxFileSize().toBytes() + " bytes");
        }
        requireOwner(request.ownerType(), request.ownerId());
        if (request.ownerType() == FileOwnerType.SELLER_PROFILE && request.documentType() == null) {
            throw new InvalidRequestException("documentType is required for SELLER_PROFILE files: "
                    + Arrays.toString(FileDocumentType.values()));
        }

        String key = objectKey(request);
        StoredFile file = repository.save(new StoredFile(key, storage.bucket(), request.fileName(),
                request.contentType(), request.sizeBytes(), request.ownerType(), request.ownerId(), request.documentType(), currentUser()));
        S3Storage.PresignedUrl upload = storage.presignUpload(key, request.contentType(), request.sizeBytes());
        return new UploadUrlDto(file.getId(), key, "PUT", upload.url(),
                Map.of("Content-Type", request.contentType(), "Content-Length", String.valueOf(request.sizeBytes())),
                upload.expiresAt());
    }

    /** Idempotent: confirming an uploaded file again just returns it. */
    @Transactional
    public StoredFileDto confirmUpload(Long fileId) {
        StoredFile file = requireFile(fileId);
        if (!file.isUploaded()) {
            S3Storage.ObjectInfo object = storage.head(file.getObjectKey())
                    .orElseThrow(() -> new ConflictException("File " + fileId + " has not been uploaded to the bucket yet"));
            file.setStatus(FileStatus.UPLOADED);
            file.setSizeBytes(object.sizeBytes());
            file.setEtag(object.etag());
            file.setUploadedAt(LocalDateTime.now());
            file = repository.saveAndFlush(file);
        }
        return toDto(file);
    }

    public StoredFileDto getFile(Long fileId) {
        return toDto(requireFile(fileId));
    }

    public DownloadUrlDto getDownloadUrl(Long fileId) {
        StoredFile file = requireFile(fileId);
        if (!file.isUploaded()) {
            throw new ConflictException("File " + fileId + " is not uploaded yet; confirm the upload first");
        }
        S3Storage.PresignedUrl download = download(file);
        return new DownloadUrlDto(file.getId(), file.getFileName(), file.getContentType(), download.url(), download.expiresAt());
    }

    public List<StoredFileDto> getFilesByOwner(FileOwnerType ownerType, Long ownerId) {
        return repository.findByOwnerTypeAndOwnerIdAndStatusOrderByUploadedAtDesc(ownerType, ownerId, FileStatus.UPLOADED)
                .stream().map(this::toDto).toList();
    }

    /** The files of every item of the promotion, so the promotion material can be made from one call. */
    public PromotionFilesDto getFilesByPromotion(Integer promotionId) {
        PromotionDto promotion = promotionService.getPromotion(promotionId)
                .orElseThrow(() -> new ResourceNotFoundException("Promotion", promotionId));
        List<PromotionItemDto> items = promotionItemService.getItemsByPromotion(promotionId);
        Map<Long, List<StoredFileDto>> filesByItem = repository.findByOwnerTypeAndOwnerIdInAndStatusOrderByUploadedAtDesc(
                        FileOwnerType.PROMOTION_ITEM, items.stream().map(PromotionItemDto::id).toList(), FileStatus.UPLOADED)
                .stream().collect(Collectors.groupingBy(StoredFile::getOwnerId,
                        Collectors.mapping(this::toDto, Collectors.toList())));
        List<PromotionItemFilesDto> itemFiles = items.stream()
                .map(item -> new PromotionItemFilesDto(item.id(), item.key(), item.description(),
                        filesByItem.getOrDefault(item.id(), List.of())))
                .toList();
        int total = itemFiles.stream().mapToInt(item -> item.files().size()).sum();
        return new PromotionFilesDto(promotion.id(), promotion.promotionName(), total, itemFiles);
    }

    /** Removes the object (if it was ever uploaded) and then the record. */
    @Transactional
    public void deleteFile(Long fileId) {
        StoredFile file = requireFile(fileId);
        storage.delete(file.getObjectKey());
        repository.delete(file);
    }

    private StoredFileDto toDto(StoredFile file) {
        return mapper.toStoredFileDto(file, file.isUploaded() ? download(file) : null);
    }

    private S3Storage.PresignedUrl download(StoredFile file) {
        return storage.presignDownload(file.getObjectKey(), file.getFileName(), file.getContentType());
    }

    private StoredFile requireFile(Long fileId) {
        return repository.findById(fileId).orElseThrow(() -> new ResourceNotFoundException("File", fileId));
    }

    private void requireOwner(FileOwnerType ownerType, Long ownerId) {
        switch (ownerType) {
            case PROMOTION_ITEM -> promotionItemService.getItem(ownerId)
                    .orElseThrow(() -> new ResourceNotFoundException("PromotionItem", ownerId));
            case SELLER_PROFILE -> sellerProfileService.getProfile(Math.toIntExact(ownerId))
                    .orElseThrow(() -> new ResourceNotFoundException("SellerProfile", ownerId));
        }
    }

    /** {prefix}/{ownerId}/{uuid}.{ext}: unique, grouped by owner, and the extension kept for viewers. */
    private static String objectKey(UploadRequestDto request) {
        Matcher extension = EXTENSION.matcher(request.fileName());
        String suffix = extension.find() ? "." + extension.group(1).toLowerCase(Locale.ROOT) : "";
        return request.ownerType().keyPrefix() + "/" + request.ownerId() + "/" + UUID.randomUUID() + suffix;
    }

    private static String currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : "anonymous";
    }
}
