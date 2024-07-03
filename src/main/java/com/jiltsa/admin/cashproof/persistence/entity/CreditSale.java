package com.jiltsa.admin.cashproof.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "credit_sales")
public class CreditSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String concept;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private Double amount;

    @NotNull
    @Column(name = "branch_id")
    private Integer branchId;

    @Column(name = "is_paid")
    private Boolean isPaid;

    @OneToMany(mappedBy = "creditSale")
    private List<Partial> partials;

    public CreditSale(String concept, LocalDateTime date, Double amount, Integer branchId) {
        this.concept = concept;
        this.date = date;
        this.amount = amount;
        this.branchId = branchId;
    }
}
