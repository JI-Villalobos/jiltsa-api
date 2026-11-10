package com.jiltsa.admin.promotions.persistence.repository;

import com.jiltsa.admin.promotions.persistence.entity.PromotionItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromotionItemRepository extends JpaRepository<PromotionItem, Long> {
    List<PromotionItem> findByPromotionIdOrderByExpirationDateAsc(Integer promotionId);
}
