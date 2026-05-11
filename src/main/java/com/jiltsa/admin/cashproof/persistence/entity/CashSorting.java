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
@Table(name = "cash_sorting")
public class CashSorting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "branch_id")
    @NotNull
    private Integer branchId;

    @Column(name = "accounting_id")
    @NotNull
    private Integer accountingId;

    @Column(name = "cash_date")
    @NotNull
    private LocalDateTime cashDate;

    @Column(name = "bt_1000")
    @NotNull
    private Integer bt1000;

    @Column(name = "bt_500")
    @NotNull
    private Integer bt500;

    @Column(name = "bt_200")
    @NotNull
    private Integer bt200;

    @Column(name = "bt_100")
    @NotNull
    private Integer bt100;

    @Column(name = "bt_50")
    @NotNull
    private Integer bt50;

    @Column(name = "bt_20")
    @NotNull
    private Integer bt20;

    @Column(name = "md_20")
    @NotNull
    private Integer md20;

    @Column(name = "md_10")
    @NotNull
    private Integer md10;

    @Column(name = "md_5")
    @NotNull
    private Integer md5;

    @Column(name = "md_2")
    @NotNull
    private Integer md2;

    @Column(name = "md_1")
    @NotNull
    private Integer md1;

    @Column(name = "md_005")
    @NotNull
    private Integer md005;

    @Column(name = "bls_10")
    @NotNull
    private Integer bls10;

    @Column(name = "bls_5")
    @NotNull
    private Integer bls5;

    @Column(name = "bls_2")
    @NotNull
    private Integer bls2;

    @Column(name = "bls_1")
    @NotNull
    private Integer bls1;
}
