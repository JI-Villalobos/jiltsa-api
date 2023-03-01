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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cash_withdrawals")
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
    private String branch;
    @NotNull
    @Column(name = "seller_name")
    private String sellerName;
}
