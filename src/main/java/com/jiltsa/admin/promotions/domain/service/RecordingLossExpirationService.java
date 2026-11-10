package com.jiltsa.admin.promotions.domain.service;

import com.jiltsa.admin.promotions.domain.dto.RecordingLossExpirationDto;
import com.jiltsa.admin.promotions.persistence.mapper.RecordingLossExpirationMapper;
import com.jiltsa.admin.promotions.persistence.repository.RecordingLossExpirationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/** Read side of the loss records; they are written only by PromotionService#closePromotion. */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecordingLossExpirationService {
    private final RecordingLossExpirationRepository repository;
    private final RecordingLossExpirationMapper mapper;

    public Optional<RecordingLossExpirationDto> getRecord(Integer recordId) {
        return repository.findById(recordId).map(mapper::toRecordingLossExpirationDto);
    }

    public Optional<RecordingLossExpirationDto> getRecordByPromotion(Integer promotionId) {
        return repository.findByPromotionId(promotionId).map(mapper::toRecordingLossExpirationDto);
    }

    public List<RecordingLossExpirationDto> getRecordsByBranch(Integer branchId, LocalDate initialDate, LocalDate finalDate) {
        return mapper.toRecordingLossExpirationDtoList(
                repository.findByBranchIdAndCreationDateBetweenOrderByCreationDateDesc(branchId, initialDate, finalDate));
    }
}
