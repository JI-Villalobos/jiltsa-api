package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.persistence.entity.ActiveAccounting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActiveAccountingRepository extends JpaRepository<ActiveAccounting, Integer> {
    Optional<ActiveAccounting> findByBranchIdAndIsActiveTrue(Integer branchId);
    Optional<ActiveAccounting> findByAccountingId(Integer accountingId);
}
