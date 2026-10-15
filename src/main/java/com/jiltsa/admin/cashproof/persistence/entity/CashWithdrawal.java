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
    private LocalDateTime date;
    @NotNull
    private Double amount;
    @NotNull
    private String concept;
    @NotNull
    @Column(name = "branch_id")
    private Integer branchId;
    @NotNull
    @Column(name = "seller_name")
    private String sellerName;

    public CashWithdrawal(LocalDateTime date, Double amount, String concept, Integer branchId, String sellerName) {
        this.date = date;
        this.amount = amount;
        this.concept = concept;
        this.branchId = branchId;
        this.sellerName = sellerName;
    }
}
