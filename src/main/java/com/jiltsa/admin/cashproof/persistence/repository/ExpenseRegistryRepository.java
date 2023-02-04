package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.persistence.entity.ExpenseRegistry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRegistryRepository extends JpaRepository<ExpenseRegistry, Integer> {
    List<ExpenseRegistry> findByAccountingId(Integer accountingId);
}
