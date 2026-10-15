package com.jiltsa.admin.sales.persistence.repository;

import com.jiltsa.admin.sales.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductBatchRepository {
    List<Product> findByBranchIdAndKeyIn(Integer branchId, Collection<String> keys);
}
