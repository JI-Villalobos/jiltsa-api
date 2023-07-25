package com.jiltsa.admin.cashproof.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.LocalDateTime;

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

    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name ="accounting_id", insertable = false, updatable = false)
    private Accounting accounting;

    @ManyToOne
    @JoinColumn(name = "expense_type_id", insertable = false, updatable = false)
    private ExpenseType expenseType;
}
