package com.jiltsa.admin.cashproof.persistence.repository;

import com.jiltsa.admin.cashproof.persistence.entity.CashWithdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashWithdrawalRepository extends JpaRepository<CashWithdrawal, Integer> {
}
