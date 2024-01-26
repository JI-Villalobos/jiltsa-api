package com.jiltsa.admin.billing.persistence.repository;

import com.jiltsa.admin.billing.persistence.entity.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Integer> {
    Page<Bill> findIsPaidTrue(Pageable pageable);
}
