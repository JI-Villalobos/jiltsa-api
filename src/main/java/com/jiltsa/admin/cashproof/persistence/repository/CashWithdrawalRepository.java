package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.persistence.entity.CashWithdrawal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CashWithdrawalRepository extends JpaRepository<CashWithdrawal, Integer> {
    List<CashWithdrawal> findByBranchId(Integer branchId);
    List<CashWithdrawal> findByBranchIdAndDateAfter(Integer branchId, LocalDateTime date);
    Page<CashWithdrawal> findByBranchIdAndDateBetween(Integer branchId, LocalDateTime start, LocalDateTime finish, Pageable page);
    Page<CashWithdrawal> findByBranchIdAndConceptContainingAndDateBetween(Integer branchId, String concept, LocalDateTime start, LocalDateTime finish, Pageable page);
}
