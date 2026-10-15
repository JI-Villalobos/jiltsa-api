package com.jiltsa.admin.sales.persistence.repository;

import com.jiltsa.admin.sales.persistence.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/** JDBC batch insert, for the same reason as {@link SaleBatchRepositoryImpl}: IDENTITY ids stop Hibernate from batching. */
@RequiredArgsConstructor
class ProductBatchRepositoryImpl implements ProductBatchRepository {
    private static final int BATCH_SIZE = 500;
    private static final String INSERT = """
            INSERT INTO `products` (`branch_id`, `key`, `category`, `description`)
            VALUES (?, ?, ?, ?)
            """;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void insertAll(List<Product> products) {
        jdbcTemplate.batchUpdate(INSERT, products, BATCH_SIZE, (ps, product) -> {
            ps.setObject(1, product.getBranchId());
            ps.setString(2, product.getKey());
            ps.setString(3, product.getCategory());
            ps.setString(4, product.getDescription());
        });
    }
}
