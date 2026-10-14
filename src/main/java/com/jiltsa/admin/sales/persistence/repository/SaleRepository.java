package com.jiltsa.admin.sales.persistence.repository;

import com.jiltsa.admin.sales.domain.dto.DailySaleTotal;
import com.jiltsa.admin.sales.persistence.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long>, SaleBatchRepository {
    List<Sale> findByBranchIdAndTimestampBetween(Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate);
    List<Sale> findByBranchId(Integer branchId);
    List<Sale> findByBranchIdAndTimestampBetweenAndCategoryIn(Integer branchId, LocalDateTime initialDate, LocalDateTime finalDate, List<String> categories);
    List<Sale> findByBranchIdAndTicketIn(Integer branchId, Collection<Long> tickets);
    List<Sale> findByTimestampBetweenAndCategoryIn(LocalDateTime initialDate, LocalDateTime finalDate, List<String> categories);

    /** Totals per branch and day in [from, to), leaving out 'TA' lines. Days without sales are absent. */
    @Query("""
            SELECT new com.jiltsa.admin.sales.domain.dto.DailySaleTotal(
                s.branchId, CAST(s.timestamp AS LocalDate), SUM(s.total), SUM(s.approximatedUtility))
            FROM Sale s
            WHERE s.timestamp >= :from AND s.timestamp < :to AND s.key <> 'TA'
            GROUP BY s.branchId, CAST(s.timestamp AS LocalDate)""")
    List<DailySaleTotal> sumDailyByBranch(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
}
