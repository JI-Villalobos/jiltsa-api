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

    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "accounting_Id", updatable = false, insertable = false)
    private Accounting accounting;

    @ManyToOne
    @JoinColumn(name = "income_type_id", insertable = false, updatable = false)
    private IncomeType incomeType;

    public IncomeRegistry(Integer accountingId, Integer incomeTypeId, Double amount, Instant time, String tag) {
        this.accountingId = accountingId;
        this.incomeTypeId = incomeTypeId;
        this.amount = amount;
        this.time = time;
        this.tag = tag;
    }
}
