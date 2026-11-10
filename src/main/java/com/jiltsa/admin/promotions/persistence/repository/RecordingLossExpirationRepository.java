package com.jiltsa.admin.promotions.persistence.repository;

import com.jiltsa.admin.promotions.persistence.entity.RecordingLossExpiration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RecordingLossExpirationRepository extends JpaRepository<RecordingLossExpiration, Integer> {
    Optional<RecordingLossExpiration> findByPromotionId(Integer promotionId);
    List<RecordingLossExpiration> findByBranchIdAndCreationDateBetweenOrderByCreationDateDesc(Integer branchId, LocalDate start, LocalDate end);
    List<RecordingLossExpiration> findByCreationDateBetweenOrderByCreationDateDesc(LocalDate start, LocalDate end);
}
