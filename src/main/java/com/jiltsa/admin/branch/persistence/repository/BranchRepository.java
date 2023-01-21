package com.jiltsa.admin.branch.persistence.repository;

import com.jiltsa.admin.branch.persistence.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
}
