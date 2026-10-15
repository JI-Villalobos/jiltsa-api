package com.jiltsa.admin.sales.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products", uniqueConstraints = @UniqueConstraint(name = "uk_products_branch_key", columnNames = {"branch_id", "key"}))
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String key;
    private String category;
    private String description;
    @Column(nullable = false)
    private Integer branchId;
}
