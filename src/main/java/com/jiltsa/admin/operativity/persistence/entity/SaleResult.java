package com.jiltsa.admin.operativity.persistence.entity;

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
@Table(name = "sale_result")
public class SaleResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pharmacy_amount")
    @NotNull
    private Double pharmacyAmount;

    @Column(name = "services_amount")
    @NotNull
    private Double servicesAmount;

    @Column(name = "week_number")
    @NotNull
    private Integer weekNumber;

    @Column
    @NotNull
    private Integer year;

    @Column(name = "initial_date")
    @NotNull
    private LocalDateTime initialDate;

    @Column(name = "final_date")
    @NotNull
    private LocalDateTime finalDate;

    @Column(name = "branch_id")
    @NotNull
    private Integer branchId;
}
