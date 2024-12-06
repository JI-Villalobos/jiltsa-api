package com.jiltsa.admin.billing.persistence.repository;

import com.jiltsa.admin.billing.persistence.entity.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface BillRepository extends JpaRepository<Bill, Integer> {
    Page<Bill> findByIsPaidFalse(Pageable pageable);
    Page<Bill> findByDateBetween(Pageable pageable, LocalDateTime start, LocalDateTime finish);
    Page<Bill> findByDateAfter(Pageable pageable, LocalDateTime date);
}
