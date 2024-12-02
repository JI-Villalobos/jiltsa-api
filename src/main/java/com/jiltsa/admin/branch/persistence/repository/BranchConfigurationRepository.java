package com.jiltsa.admin.branch.persistence.repository;

import com.jiltsa.admin.branch.persistence.entity.BranchConfiguration;
import com.jiltsa.admin.branch.persistence.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BranchConfigurationRepository extends JpaRepository<BranchConfiguration, Integer> {
    Optional<BranchConfiguration> findByBranchId(Integer branchId);
    List<BranchConfiguration> findByProfile(Profile profile);
}
