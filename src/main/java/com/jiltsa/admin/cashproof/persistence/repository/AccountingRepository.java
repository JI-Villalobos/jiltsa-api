package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.persistence.entity.Accounting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AccountingRepository extends JpaRepository<Accounting, Integer> {
    //TODO:should find by branch too
    List<Accounting> findTop20ByOrderByIdDesc();
    List<Accounting> findByDateBetween(LocalDateTime start, LocalDateTime end);
}
