package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.persistence.entity.Accounting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AccountingRepository extends JpaRepository<Accounting, Integer> {
    Page<Accounting> findByDateBetweenAndBranchIdOrderByDateAsc(Pageable pageable, LocalDateTime start, LocalDateTime end, Integer branchId);
    List<Accounting> findByBranchIdAndDateAfterOrderByDateAsc(Integer branchId, LocalDateTime date);
    Page<Accounting> findByDateAfterOrderByDateAsc(Pageable pageable, LocalDateTime date);
    Page<Accounting> findByBranchIdAndDateAfterOrderByDateDesc(Pageable pageable, Integer branchId, LocalDateTime date);
}
