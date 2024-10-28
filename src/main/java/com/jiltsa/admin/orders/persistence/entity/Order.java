package com.jiltsa.admin.orders.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "provider_id")
    @NotNull
    private Integer providerId;

    @Column(name = "branch_id")
    @NotNull
    private Integer branchId;

    @Column(name = "creation_date")
    @NotNull
    private LocalDate creationDate;

    @Column(name = "estimated_cost")
    @NotNull
    private Double estimatedCost;

    @Column(name = "real_cost")
    @NotNull
    private Double realCost;

    @Column(name = "status")
    @NotNull
    private Integer status;

    @Column(name = "is_open")
    @NotNull
    private Boolean isOpen;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items;


    public Order(Integer providerId, Integer branchId, LocalDate creationDate, Double estimatedCost, Double realCost,
                 Integer status, Boolean isOpen) {
        this.providerId = providerId;
        this.branchId = branchId;
        this.creationDate = creationDate;
        this.estimatedCost = estimatedCost;
        this.realCost = realCost;
        this.status = status;
        this.isOpen = isOpen;
    }
}
