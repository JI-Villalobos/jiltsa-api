package com.jiltsa.admin.promotions.domain.service;

import com.jiltsa.admin.common.exception.ConflictException;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import com.jiltsa.admin.promotions.domain.dto.PromotionItemDto;
import com.jiltsa.admin.promotions.persistence.entity.Promotion;
import com.jiltsa.admin.promotions.persistence.entity.PromotionItem;
import com.jiltsa.admin.promotions.persistence.mapper.PromotionItemMapper;
import com.jiltsa.admin.promotions.persistence.repository.PromotionItemRepository;
import com.jiltsa.admin.promotions.persistence.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Items belong to an active promotion; once it is closed its items are history and stay as they are. */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PromotionItemService {
    private final PromotionItemRepository repository;
    private final PromotionRepository promotionRepository;
    private final PromotionItemMapper mapper;

    public Optional<PromotionItemDto> getItem(Long itemId) {
        return repository.findById(itemId).map(mapper::toPromotionItemDto);
    }

    public List<PromotionItemDto> getItemsByPromotion(Integer promotionId) {
        return mapper.toPromotionItemDtoList(repository.findByPromotionIdOrderByExpirationDateAsc(promotionId));
    }

    @Transactional
    public PromotionItemDto createItem(PromotionItemDto itemDto) {
        requireActivePromotions(Set.of(itemDto.promotionId()));
        PromotionItem item = mapper.toPromotionItem(itemDto);
        item.setId(null);
        return mapper.toPromotionItemDto(repository.save(item));
    }

    /** All or nothing: every item's promotion must exist and be active. */
    @Transactional
    public List<PromotionItemDto> createItems(List<PromotionItemDto> itemDtoList) {
        requireActivePromotions(itemDtoList.stream().map(PromotionItemDto::promotionId).collect(Collectors.toSet()));
        List<PromotionItem> items = mapper.toPromotionItemList(itemDtoList);
        items.forEach(item -> item.setId(null));
        return mapper.toPromotionItemDtoList(repository.saveAll(items));
    }

    @Transactional
    public PromotionItemDto updateItem(Long itemId, PromotionItemDto itemDto) {
        PromotionItem item = requireItem(itemId);
        requireActivePromotions(Stream.of(item.getPromotionId(), itemDto.promotionId()).collect(Collectors.toSet()));
        item.setKey(itemDto.key());
        item.setDescription(itemDto.description());
        item.setQuantity(itemDto.quantity());
        item.setNormalPrice(itemDto.normalPrice());
        item.setPromotionPrice(itemDto.promotionPrice());
        item.setPurchasePrice(itemDto.purchasePrice());
        item.setPromotionId(itemDto.promotionId());
        item.setExpirationDate(itemDto.expirationDate());
        return mapper.toPromotionItemDto(repository.saveAndFlush(item));
    }

    @Transactional
    public void deleteItem(Long itemId) {
        PromotionItem item = requireItem(itemId);
        requireActivePromotions(Set.of(item.getPromotionId()));
        repository.delete(item);
    }

    private PromotionItem requireItem(Long itemId) {
        return repository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("PromotionItem", itemId));
    }

    private void requireActivePromotions(Set<Integer> promotionIds) {
        List<Promotion> promotions = promotionRepository.findAllById(promotionIds);
        Set<Integer> found = promotions.stream().map(Promotion::getId).collect(Collectors.toSet());
        promotionIds.stream().filter(id -> !found.contains(id)).findFirst()
                .ifPresent(id -> { throw new ResourceNotFoundException("Promotion", id); });
        promotions.stream().filter(promotion -> !promotion.getActive()).findFirst()
                .ifPresent(promotion -> { throw new ConflictException("Promotion " + promotion.getId() + " is closed; its items cannot change"); });
    }
}
