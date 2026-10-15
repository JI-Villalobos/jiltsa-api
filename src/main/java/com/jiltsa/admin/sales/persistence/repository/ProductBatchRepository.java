package com.jiltsa.admin.sales.persistence.repository;

import com.jiltsa.admin.sales.persistence.entity.Product;

import java.util.List;

public interface ProductBatchRepository {
    /** Inserts the products as JDBC batches; ids are not read back. */
    void insertAll(List<Product> products);
}
