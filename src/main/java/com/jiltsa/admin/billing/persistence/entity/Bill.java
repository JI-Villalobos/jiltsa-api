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
@Table(name = "bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private String invoice;

    @NotNull
    private String branch;

    @NotNull
    @Column(name = "branch_id")
    private Integer branchId;

    @NotNull
    private Double amount;

    @NotNull
    @Column(name = "limit_payment_date")
    private LocalDateTime limitPaymentDate;

    @NotNull
    @Column(name = "is_paid")
    private Boolean isPaid;

    @Column(name = "payment_ticket")
    private String paymentTicket;

    @NotNull
    @Column(name = "provider_id")
    private Integer providerId;

    @NotNull
    @Column(name = "is-active")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "provider_id", insertable = false, updatable = false)
    private Provider provider;

    public Bill(LocalDateTime date, String invoice, String branch,
                Integer branchId, Double amount, LocalDateTime limitPaymentDate, Integer providerId, Boolean isActive) {
        this.date = date;
        this.invoice = invoice;
        this.branch = branch;
        this.branchId = branchId;
        this.amount = amount;
        this.limitPaymentDate = limitPaymentDate;
        this.providerId = providerId;
        this.isActive = isActive;
    }

    public Bill(LocalDateTime date, String invoice, String branch, Integer branchId, Double amount,
                LocalDateTime limitPaymentDate, Boolean isPaid, String paymentTicket, Integer providerId, Boolean isActive) {
        this.date = date;
        this.invoice = invoice;
        this.branch = branch;
        this.branchId = branchId;
        this.amount = amount;
        this.limitPaymentDate = limitPaymentDate;
        this.isPaid = isPaid;
        this.paymentTicket = paymentTicket;
        this.providerId = providerId;
        this.isActive = isActive;
    }
}
