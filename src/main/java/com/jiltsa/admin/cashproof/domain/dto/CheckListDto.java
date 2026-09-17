package com.jiltsa.admin.cashproof.domain.dto;

import com.jiltsa.admin.cashproof.persistence.entity.CheckType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CheckListDto(
        Integer id, @NotNull CheckType checkType,
        @NotNull LocalDate date, @NotNull Double cashBalance,
        @NotNull Double tranboxBalance, @NotNull Integer cellphoneCharge,
        @NotNull Boolean cellphoneCondition, String cellphoneObservation,
        @NotNull Byte furnitureCleanConditions,
        @NotNull Boolean installationState,
        String installationStateObservation,
        @NotNull Integer sellerId, @NotNull Integer accountingId) {

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
