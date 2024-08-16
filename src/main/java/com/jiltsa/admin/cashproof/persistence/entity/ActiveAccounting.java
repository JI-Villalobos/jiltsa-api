package com.jiltsa.admin.cashproof.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    Integer accountingId;

    @Column(name = "is_active")
    @NotNull
    Boolean isActive;

    @Column(name = "branch_id")
    @NotNull
    Integer branchId;

    @Column(name = "seller_id")
    @NotNull
    Integer sellerId;
}
