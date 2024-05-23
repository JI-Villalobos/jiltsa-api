package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.persistence.entity.CashWithdrawal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CashWithdrawalRepository extends JpaRepository<CashWithdrawal, Integer> {
    List<CashWithdrawal> findByBranch(String branch);
    List<CashWithdrawal> findByBranchAndDateAfter(String branch, LocalDateTime date);
    Page<CashWithdrawal> findByBranchAndDateAfter(String branch, LocalDateTime date, Pageable page);
    Page<CashWithdrawal> findByBranchAndConceptContainingAndDateAfter(String branch, String concept, LocalDateTime date, Pageable page);
}
