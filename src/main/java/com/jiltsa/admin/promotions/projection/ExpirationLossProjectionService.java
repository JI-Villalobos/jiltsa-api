package com.jiltsa.admin.promotions.projection;

import com.jiltsa.admin.promotions.domain.dto.ExpirationLossProjectionDto;
import com.jiltsa.admin.promotions.domain.dto.PromotionItemLossDto;
import com.jiltsa.admin.promotions.domain.dto.PromotionLossDto;
import com.jiltsa.admin.promotions.persistence.entity.Promotion;
import com.jiltsa.admin.promotions.persistence.entity.PromotionItem;
import com.jiltsa.admin.promotions.persistence.entity.RecordingLossExpiration;
import com.jiltsa.admin.promotions.persistence.repository.PromotionRepository;
import com.jiltsa.admin.promotions.persistence.repository.RecordingLossExpirationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Losses recorded within a range (by the record's creation date), totalled and broken down into
 * the promotions that caused them and the medicines each one left unsold.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExpirationLossProjectionService {
    private final RecordingLossExpirationRepository lossRepository;
    private final PromotionRepository promotionRepository;

    /** {@code branchId} null means every branch. */
    public ExpirationLossProjectionDto getExpirationLosses(Integer branchId, LocalDate initialDate, LocalDate finalDate) {
        List<RecordingLossExpiration> records = branchId == null
                ? lossRepository.findByCreationDateBetweenOrderByCreationDateDesc(initialDate, finalDate)
                : lossRepository.findByBranchIdAndCreationDateBetweenOrderByCreationDateDesc(branchId, initialDate, finalDate);

        Map<Integer, Promotion> promotions = promotionRepository
                .findAllById(records.stream().map(RecordingLossExpiration::getPromotionId).toList()).stream()
                .collect(Collectors.toMap(Promotion::getId, Function.identity()));

        List<PromotionLossDto> promotionLosses = records.stream()
                .map(record -> toPromotionLoss(record, promotions.get(record.getPromotionId())))
                .toList();
        double totalLoss = records.stream().mapToDouble(RecordingLossExpiration::getTotalLoss).sum();
        return new ExpirationLossProjectionDto(branchId, initialDate, finalDate, totalLoss, records.size(), promotionLosses);
    }

    private static PromotionLossDto toPromotionLoss(RecordingLossExpiration record, Promotion promotion) {
        List<PromotionItemLossDto> items = promotion.getItems().stream()
                .sorted((a, b) -> Double.compare(b.loss(), a.loss()))
                .map(ExpirationLossProjectionService::toItemLoss)
                .toList();
        return new PromotionLossDto(record.getId(), promotion.getId(), promotion.getPromotionName(), record.getBranchId(),
                promotion.getPromotionEndDate(), record.getCreationDate(), record.getTotalLoss(), items);
    }

    private static PromotionItemLossDto toItemLoss(PromotionItem item) {
        return new PromotionItemLossDto(item.getId(), item.getKey(), item.getDescription(), item.getQuantity(),
                item.getNormalPrice(), item.getPromotionPrice(), item.getPurchasePrice(), item.getExpirationDate(), item.loss());
    }
}
