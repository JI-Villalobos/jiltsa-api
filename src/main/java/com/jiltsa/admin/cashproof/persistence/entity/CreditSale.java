package com.jiltsa.admin.cashproof.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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

    @Column(name = "is_paid")
    private Boolean isPaid;

    public CreditSale(String concept, LocalDateTime date, Double amount) {
        this.concept = concept;
        this.date = date;
        this.amount = amount;
    }
}
