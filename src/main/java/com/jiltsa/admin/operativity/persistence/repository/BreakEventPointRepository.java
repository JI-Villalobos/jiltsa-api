package com.jiltsa.admin.operativity.persistence.repository;

import com.jiltsa.admin.operativity.persistence.entity.BreakEvenPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BreakEventPointRepository extends JpaRepository<BreakEvenPoint, Integer> {
    Optional<BreakEvenPoint> findByBranchId(Integer branchId);
}
