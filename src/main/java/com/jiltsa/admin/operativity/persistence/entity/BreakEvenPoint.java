package com.jiltsa.admin.operativity.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "break_even_point")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BreakEvenPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "branch_id")
    private Integer branchId;

    @NotNull
    private Double paysheet;

    @NotNull
    private Double rent;

    @NotNull
    @Column(name = "electricity_service")
    private Double electricityService;

    @NotNull
    private Double internet;

    @NotNull
    @Column(name = "other_services")
    private Double otherServices;

    @NotNull
    @Column(name = "gasoline_and_transport")
    private Double gasolineAndTransport;

    @NotNull
    @Column(name = "admin_payment")
    private Double adminPayment;

    @NotNull
    @Column(name = "operating_cost")
    private Double operatingCost;

    @NotNull
    @Column(name = "other_expenses")
    private  Double otherExpenses;
    @NotNull
    @Column(name = "fixed_expenses")
    private Double fixedExpenses;

    @NotNull
    private Double pe;
}
