package com.jiltsa.admin.cashproof.persistence.entity;

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
@Table(name = "active_accounting")
public class ActiveAccounting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "accounting_id")
    Integer accountingId;

    @Column(name = "is_active")
    Boolean isActive;

    @Column(name = "branch_id")
    Integer branchId;

    @Column(name = "seller_id")
    Integer sellerId;
}
