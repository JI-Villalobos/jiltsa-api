package com.jiltsa.admin.promotions.persistence.repository;

import com.jiltsa.admin.promotions.persistence.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
    List<Promotion> findByBranchIdAndCreationDateBetweenOrderByCreationDateDesc(Integer branchId, LocalDate start, LocalDate end);
    List<Promotion> findByBranchIdAndActiveTrueOrderByPromotionEndDateAsc(Integer branchId);
}
