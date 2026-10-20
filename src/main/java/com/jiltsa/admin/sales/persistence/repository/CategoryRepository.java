package com.jiltsa.admin.sales.persistence.repository;

import com.jiltsa.admin.sales.persistence.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findAllByOrderByCategoryName();

    /** Compares names with the column's case-insensitive collation. */
    List<Category> findByCategoryNameIn(Collection<String> categoryNames);
}
