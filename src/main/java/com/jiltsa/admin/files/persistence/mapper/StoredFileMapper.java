package com.jiltsa.admin.files.persistence.mapper;

import com.jiltsa.admin.files.domain.dto.StoredFileDto;
import com.jiltsa.admin.files.persistence.entity.StoredFile;
import com.jiltsa.admin.files.storage.S3Storage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StoredFileMapper {
    /** {@code download} may be null (file still pending): the URL fields are then null. */
    @Mapping(target = "downloadUrl", source = "download.url")
    @Mapping(target = "downloadUrlExpiresAt", source = "download.expiresAt")
    StoredFileDto toStoredFileDto(StoredFile file, S3Storage.PresignedUrl download);
}
