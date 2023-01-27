package com.jiltsa.admin.cashproof.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class CashWithdrawal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime date = LocalDateTime.now();
    @NotNull
    private Double amount;
    @NotNull
    private String concept;
    @NotNull
    @Column(name = "seller_name")
    private String sellerName;
}
