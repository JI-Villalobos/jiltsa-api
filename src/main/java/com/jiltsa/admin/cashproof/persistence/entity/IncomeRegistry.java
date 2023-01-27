package com.jiltsa.admin.cashproof.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cash_registry")
public class IncomeRegistry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Column(name = "accounting_Id")
    private Integer accountingId;
    @NotNull
    @Column(name = "income_type_id")
    private Integer incomeTypeId;
    @NotNull
    private Double amount;
    private Instant time = Instant.now();
    @NotNull
    private String tag;

    @ManyToOne
    @JoinColumn(name = "accounting_Id", updatable = false, insertable = false)
    private Accounting accounting;

    @ManyToOne
    @JoinColumn(name = "income_type_id", insertable = false, updatable = false)
    private IncomeType incomeType;
}
