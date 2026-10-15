package com.jiltsa.admin.sales.persistence.repository;

import com.jiltsa.admin.sales.persistence.entity.Sale;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.List;

/**
 * Sale ids are IDENTITY-generated, which stops Hibernate from batching inserts
 * (one round trip per row). Going through JDBC keeps a sales upload to a few
 * statements; it joins the caller's transaction, so a failed batch rolls back whole.
 */
@RequiredArgsConstructor
class SaleBatchRepositoryImpl implements SaleBatchRepository {
    private static final int BATCH_SIZE = 500;
    private static final String INSERT = """
            INSERT INTO `sales` (`branch_id`, `key`, `description`, `ticket`, `category`, `quantity`, `price`,
                                 `purchase_price`, `approximate_utility`, `total`, `timestamp`, `user`)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void insertAll(List<Sale> sales) {
        jdbcTemplate.batchUpdate(INSERT, sales, BATCH_SIZE, (ps, sale) -> {
            ps.setObject(1, sale.getBranchId());
            ps.setString(2, sale.getKey());
            ps.setString(3, sale.getDescription());
            ps.setObject(4, sale.getTicket());
            ps.setString(5, sale.getCategory());
            ps.setObject(6, sale.getQuantity());
            ps.setObject(7, sale.getPrice());
            ps.setObject(8, sale.getPurchasePrice());
            ps.setObject(9, sale.getApproximatedUtility());
            ps.setObject(10, sale.getTotal());
            ps.setTimestamp(11, Timestamp.valueOf(sale.getTimestamp()));
            ps.setString(12, sale.getUser());
        });
    }
}
