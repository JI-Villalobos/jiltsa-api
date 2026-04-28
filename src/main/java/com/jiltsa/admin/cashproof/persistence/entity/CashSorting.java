package com.jiltsa.admin.cashproof.persistence.entity;

import jakarta.persistence.*;
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
    private Integer branchId;

    @Column(name = "accounting_id")
    private Integer accountingId;

    @Column(name = "cash_date")
    private LocalDateTime cashDate;

    @Column(name = "bt_1000")
    private Integer bt1000;

    @Column(name = "bt_500")
    private Integer bt500;

    @Column(name = "bt_200")
    private Integer bt200;

    @Column(name = "bt_100")
    private Integer bt100;

    @Column(name = "bt_50")
    private Integer bt50;

    @Column(name = "bt_20")
    private Integer bt20;

    @Column(name = "md_20")
    private Integer md20;

    @Column(name = "md_10")
    private Integer md10;

    @Column(name = "md_5")
    private Integer md5;

    @Column(name = "md_2")
    private Integer md2;

    @Column(name = "md_1")
    private Integer md1;

    @Column(name = "md_005")
    private Integer md005;

    @Column(name = "bls_10")
    private Integer bls10;

    @Column(name = "bls_5")
    private Integer bls5;

    @Column(name = "bls_2")
    private Integer bls2;

    @Column(name = "bls_1")
    private Integer bls1;
}
