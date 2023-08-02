package com.jiltsa.admin.branch.persistence.repository;

import com.jiltsa.admin.branch.persistence.entity.BranchConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchConfigurationRepository extends JpaRepository<BranchConfiguration, Integer> {
}
