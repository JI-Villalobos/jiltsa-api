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
@Table(name = "partial_payments")
public class Partial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "credit_sale_id")
    private Integer creditSaleId;

    @NotNull
    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @NotNull
    private Double amount;

    public Partial(Integer creditSaleId, LocalDateTime paymentDate, Double amount) {
        this.creditSaleId = creditSaleId;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }
}
