package com.jiltsa.admin.cashproof.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "check_list")
public class CheckList {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "check_type")
    @NotNull
    private CheckType checkType;

    @NotNull
    private LocalDate date;

    @Column(name = "cash_balance")
    @NotNull
    private Double cashBalance;

    @Column(name = "tranbox_balance")
    @NotNull
    private Double tranboxBalance;

    @Column(name = "cellphone_charge")
    @NotNull
    private Integer cellphoneCharge;

    @Column(name = "cellphone_condition")
    @NotNull
    private Boolean cellphoneCondition;

    @Column(name = "cellphone_observation")
    private String cellphoneObservation;

    @Column(name = "furniture_clean_conditions")
    @NotNull
    private Byte furnitureCleanConditions;

    @Column(name = "installation_state")
    @NotNull
    private Boolean installationState;

    @Column(name = "installation_state_observation")
    private String installationStateObservation;

    @Column(name = "seller_id")
    @NotNull
    private Integer sellerId;

    @Column(name = "accounting_id")
    @NotNull
    private Integer accountingId;


    public CheckList(CheckType checkType, LocalDate date, Double cashBalance, Double tranboxBalance, Integer cellphoneCharge,
                     Boolean cellphoneCondition, String cellphoneObservation, Byte furnitureCleanConditions,
                     Boolean installationState, String installationStateObservation, Integer sellerId,
                     Integer accountingId) {
        this.checkType = checkType;
        this.date = date;
        this.cashBalance = cashBalance;
        this.tranboxBalance = tranboxBalance;
        this.cellphoneCharge = cellphoneCharge;
        this.cellphoneCondition = cellphoneCondition;
        this.cellphoneObservation = cellphoneObservation;
        this.furnitureCleanConditions = furnitureCleanConditions;
        this.installationState = installationState;
        this.installationStateObservation = installationStateObservation;
        this.sellerId = sellerId;
        this.accountingId = accountingId;
    }
}
