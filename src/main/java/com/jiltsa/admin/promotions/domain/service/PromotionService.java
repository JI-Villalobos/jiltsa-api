package com.jiltsa.admin.promotions.domain.service;

import com.jiltsa.admin.branch.domain.service.BranchService;
import com.jiltsa.admin.common.exception.ConflictException;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import com.jiltsa.admin.promotions.domain.dto.PromotionDto;
import com.jiltsa.admin.promotions.domain.dto.RecordingLossExpirationDto;
import com.jiltsa.admin.promotions.persistence.entity.Promotion;
import com.jiltsa.admin.promotions.persistence.entity.PromotionItem;
import com.jiltsa.admin.promotions.persistence.entity.RecordingLossExpiration;
import com.jiltsa.admin.promotions.persistence.mapper.PromotionMapper;
import com.jiltsa.admin.promotions.persistence.mapper.RecordingLossExpirationMapper;
import com.jiltsa.admin.promotions.persistence.repository.PromotionItemRepository;
import com.jiltsa.admin.promotions.persistence.repository.PromotionRepository;
import com.jiltsa.admin.promotions.persistence.repository.RecordingLossExpirationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * A promotion is created open (active), gets its items through PromotionItemService, and is
 * closed once: closing deactivates it and records the loss of what was left at purchase price.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PromotionService {
    private final PromotionRepository repository;
    private final PromotionItemRepository itemRepository;
    private final RecordingLossExpirationRepository lossRepository;
    private final PromotionMapper mapper;
    private final RecordingLossExpirationMapper lossMapper;
    private final BranchService branchService;

    public Optional<PromotionDto> getPromotion(Integer promotionId) {
        return repository.findById(promotionId).map(mapper::toPromotionDto);
    }

    public List<PromotionDto> getPromotionsByBranch(Integer branchId, LocalDate initialDate, LocalDate finalDate) {
        return mapper.toPromotionDtoList(
                repository.findByBranchIdAndCreationDateBetweenOrderByCreationDateDesc(branchId, initialDate, finalDate));
    }

    public List<PromotionDto> getActivePromotionsByBranch(Integer branchId) {
        return mapper.toPromotionDtoList(repository.findByBranchIdAndActiveTrueOrderByPromotionEndDateAsc(branchId));
    }

    @Transactional
    public PromotionDto createPromotion(PromotionDto promotionDto) {
        requireBranch(promotionDto.branchId());
        Promotion promotion = mapper.toPromotion(promotionDto);
        promotion.setId(null);
        if (promotion.getCreationDate() == null) promotion.setCreationDate(LocalDate.now());
        if (promotion.getActive() == null) promotion.setActive(true);
        rejectEndBeforeCreation(promotion);
        return mapper.toPromotionDto(repository.save(promotion));
    }

    /** Name, branch, dates and the active flag may change; items only through PromotionItemService. */
    @Transactional
    public PromotionDto updatePromotion(Integer promotionId, PromotionDto promotionDto) {
        Promotion promotion = requirePromotion(promotionId);
        if (lossRepository.findByPromotionId(promotionId).isPresent() && Boolean.TRUE.equals(promotionDto.active())) {
            throw new ConflictException("Promotion " + promotionId + " is closed and its loss recorded; it cannot be reactivated");
        }
        requireBranch(promotionDto.branchId());
        promotion.setPromotionName(promotionDto.promotionName());
        promotion.setBranchId(promotionDto.branchId());
        if (promotionDto.creationDate() != null) promotion.setCreationDate(promotionDto.creationDate());
        promotion.setPromotionEndDate(promotionDto.promotionEndDate());
        if (promotionDto.active() != null) promotion.setActive(promotionDto.active());
        rejectEndBeforeCreation(promotion);
        return mapper.toPromotionDto(repository.saveAndFlush(promotion));
    }

    /**
     * Ends the promotion: it becomes inactive and the value of its remaining items, at purchase
     * price, is recorded as the expiration loss of the branch. A promotion is closed once.
     */
    @Transactional
    public RecordingLossExpirationDto closePromotion(Integer promotionId) {
        Promotion promotion = requirePromotion(promotionId);
        if (lossRepository.findByPromotionId(promotionId).isPresent()) {
            throw new ConflictException("Promotion " + promotionId + " is already closed and its loss recorded");
        }
        double totalLoss = itemRepository.findByPromotionIdOrderByExpirationDateAsc(promotionId).stream()
                .mapToDouble(PromotionItem::loss).sum();
        promotion.setActive(false);
        repository.save(promotion);
        RecordingLossExpiration record = lossRepository.save(
                new RecordingLossExpiration(promotionId, promotion.getBranchId(), totalLoss, LocalDate.now()));
        return lossMapper.toRecordingLossExpirationDto(record);
    }

    /** Items go with the promotion (ON DELETE CASCADE); a promotion with a recorded loss stays. */
    @Transactional
    public void deletePromotion(Integer promotionId) {
        Promotion promotion = requirePromotion(promotionId);
        if (lossRepository.findByPromotionId(promotionId).isPresent()) {
            throw new ConflictException("Promotion " + promotionId + " has its loss recorded and cannot be deleted");
        }
        repository.delete(promotion);
    }

    private Promotion requirePromotion(Integer promotionId) {
        return repository.findById(promotionId)
                .orElseThrow(() -> new ResourceNotFoundException("Promotion", promotionId));
    }

    private void requireBranch(Integer branchId) {
        branchService.getById(branchId).orElseThrow(() -> new ResourceNotFoundException("Branch", branchId));
    }

    private static void rejectEndBeforeCreation(Promotion promotion) {
        if (promotion.getPromotionEndDate().isBefore(promotion.getCreationDate())) {
            throw new ConflictException("promotionEndDate " + promotion.getPromotionEndDate()
                    + " is before creationDate " + promotion.getCreationDate());
        }
    }
}
