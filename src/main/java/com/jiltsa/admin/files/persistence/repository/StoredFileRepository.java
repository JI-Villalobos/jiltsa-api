package com.jiltsa.admin.files.persistence.repository;

import com.jiltsa.admin.files.domain.FileOwnerType;
import com.jiltsa.admin.files.domain.FileStatus;
import com.jiltsa.admin.files.persistence.entity.StoredFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface StoredFileRepository extends JpaRepository<StoredFile, Long> {
    List<StoredFile> findByOwnerTypeAndOwnerIdAndStatusOrderByUploadedAtDesc(FileOwnerType ownerType, Long ownerId, FileStatus status);
    List<StoredFile> findByOwnerTypeAndOwnerIdInAndStatusOrderByUploadedAtDesc(FileOwnerType ownerType, Collection<Long> ownerIds, FileStatus status);
}
