package com.jiltsa.admin.sales.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sales", uniqueConstraints = @UniqueConstraint(name = "uk_sales_branch_ticket_key", columnNames = {"branch_id", "ticket", "key"}))
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "branch_id", nullable = false)
    private Integer branchId;
    @Column(nullable = false)
    private String key;
    private String description;
    @Column(nullable = false)
    private Long ticket;
    private String category;
    private Integer quantity;
    private Double price;
    @Column(name = "purchase_price")
    private Double purchasePrice;
    @Column(name = "approximate_utility")
    private Double approximatedUtility;
    private Double total;
    private LocalDateTime timestamp;
    private String user;
}
