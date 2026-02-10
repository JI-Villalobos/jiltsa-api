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
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "branch_id")
    private Integer branchId;
    private String key;
    private String description;
    private String category;
    private Integer quantity;
    private Double price;
    private Double amount;
    @Column(name = "purchase_price")
    private Double purchasePrice;
    @Column(name = "approximate_utility")
    private Double approximateUtility;
    private Long ticket;
    private String user;
    @Column(name = "sale_date")
    private LocalDateTime saleDate;
}
