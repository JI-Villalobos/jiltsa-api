package com.jiltsa.admin.operativity.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "operative_expense")
public class OperativeExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "branch_id")
    @NotNull
    private Integer branchId;

    private String invoice;

    @Column(name = "expense_date")
    @NotNull
    private LocalDateTime expenseDate;

    @Column(name = "provider_id")
    private Integer providerId;

    @NotNull
    private Double amount;

    @NotNull
    private String concept;

    @NotNull
    private String category;
}
