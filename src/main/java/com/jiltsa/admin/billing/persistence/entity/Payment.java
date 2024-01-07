package com.jiltsa.admin.billing.persistence.entity;

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
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private Double amount;

    @NotNull
    private String ticket;

    public Payment(LocalDateTime date, Double amount, String ticket) {
        this.date = date;
        this.amount = amount;
        this.ticket = ticket;
    }
}
