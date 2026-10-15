package com.jiltsa.admin.sales.persistence.repository;

import com.jiltsa.admin.sales.persistence.entity.Sale;

import java.util.List;

public interface SaleBatchRepository {
    /** Inserts the sales as JDBC batches; ids are not read back. */
    void insertAll(List<Sale> sales);
}
