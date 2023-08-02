package com.jiltsa.admin.branch.persistence.repository;

import com.jiltsa.admin.branch.domain.dto.TotalBalanceDto;
import com.jiltsa.admin.branch.persistence.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
    @Procedure(value = "get_totals", outputParameterName = "totals")
    Double getTotalBalance(@Param("selected_branch_id") Integer selectedBranchId, @Param("branch_name") String branchName);
}
