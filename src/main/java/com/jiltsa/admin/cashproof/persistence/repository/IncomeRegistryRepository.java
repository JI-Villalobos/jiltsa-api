package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.persistence.entity.IncomeRegistry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeRegistryRepository extends JpaRepository<IncomeRegistry, Integer> {
    List<IncomeRegistry> findByAccountingId(Integer accountingId);
}
