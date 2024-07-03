package com.jiltsa.admin.branch.persistence.repository;

import com.jiltsa.admin.branch.persistence.entity.BranchConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchConfigurationRepository extends JpaRepository<BranchConfiguration, Integer> {
    Optional<BranchConfiguration> findByBranchId(Integer branchId);
}
