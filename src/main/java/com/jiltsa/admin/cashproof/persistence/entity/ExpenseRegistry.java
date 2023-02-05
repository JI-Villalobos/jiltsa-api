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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expense_registry")
public class ExpenseRegistry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Column(name = "accounting_id")
    private Integer accountingId;
    @NotNull
    @Column(name = "expense_type_id")
    private Integer expenseTypeId;
    @NotNull
    private String description;
    private Instant time = Instant.now();
    @NotNull
    private Double amount;

    @ManyToOne
    @JoinColumn(name ="accounting_id", insertable = false, updatable = false)
    private Accounting accounting;

    @ManyToOne
    @JoinColumn(name = "expense_type_id", insertable = false, updatable = false)
    private ExpenseType expenseType;
}
