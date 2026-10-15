package com.jiltsa.admin.sales.domain.service;

import com.jiltsa.admin.common.exception.ConflictException;
import com.jiltsa.admin.sales.domain.dto.CreateProductDto;
import com.jiltsa.admin.sales.domain.dto.ProductBatchResultDto;
import com.jiltsa.admin.sales.persistence.entity.Product;
import com.jiltsa.admin.sales.persistence.mapper.ProductMapper;
import com.jiltsa.admin.sales.persistence.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    /**
     * All or nothing. A key may appear once per branch, so the batch is rejected if it
     * repeats a key within a branch or reuses one already stored for that branch.
     * The unique index on (branch_id, key) backs this up against concurrent uploads.
     */
    @Transactional
    public ProductBatchResultDto createProducts(List<CreateProductDto> products) {
        Map<Integer, Set<String>> keysByBranch = new TreeMap<>();
        List<String> repeated = new ArrayList<>();
        for (CreateProductDto product : products) {
            if (!keysByBranch.computeIfAbsent(product.branchId(), branch -> new LinkedHashSet<>()).add(product.key())) {
                repeated.add(describe(product.branchId(), product.key()));
            }
        }
        if (!repeated.isEmpty()) {
            throw new ConflictException("Keys repeated within the batch: " + String.join(", ", repeated));
        }

        List<String> existing = keysByBranch.entrySet().stream()
                .flatMap(entry -> repository.findByBranchIdAndKeyIn(entry.getKey(), entry.getValue()).stream())
                .map(product -> describe(product.getBranchId(), product.getKey()))
                .toList();
        if (!existing.isEmpty()) {
            throw new ConflictException("Products already exist: " + String.join(", ", existing));
        }

        repository.insertAll(mapper.toProductList(products));
        return new ProductBatchResultDto(products.size());
    }

    private static String describe(Integer branchId, String key) {
        return "branch " + branchId + " key " + key;
    }
}
