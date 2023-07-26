package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.persistence.entity.Accounting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AccountingRepository extends JpaRepository<Accounting, Integer> {
    List<Accounting> findByDateBetweenAndBranchIdOrderByDateAsc(LocalDateTime start, LocalDateTime end, Integer branchId);
    List<Accounting> findByBranchIdAndDateAfterOrderByDateAsc(Integer branchId, LocalDateTime date);
}
