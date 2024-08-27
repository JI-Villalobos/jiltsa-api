package com.jiltsa.admin.cashproof.domain.dto;

import com.jiltsa.admin.cashproof.persistence.entity.CheckType;

import java.time.LocalDate;

public record CheckListDto(
        Integer id, CheckType checkType,
        LocalDate date, Double cashBalance,
        Double tranboxBalance, Integer cellphoneCharge,
        Boolean cellphoneCondition, String cellphoneObservation,
        Byte furnitureCleanConditions,
        Boolean installationState,
        String installationStateObservation,
        Integer sellerId, Integer accountingId) {

    public CheckListDto(CheckType checkType, LocalDate date, Double cashBalance, Double tranboxBalance,
                        Integer cellphoneCharge, Boolean cellphoneCondition, String cellphoneObservation,
                        Byte furnitureCleanConditions, Boolean installationState, String installationStateObservation,
                        Integer sellerId, Integer accountingId) {
        this(0, checkType, date, cashBalance, tranboxBalance, cellphoneCharge, cellphoneCondition,
                cellphoneObservation, furnitureCleanConditions, installationState, installationStateObservation,
                sellerId, accountingId
        );
    }
}
