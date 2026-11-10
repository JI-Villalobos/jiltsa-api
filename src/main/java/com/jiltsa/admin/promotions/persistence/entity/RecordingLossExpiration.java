package com.jiltsa.admin.promotions.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/** The loss written off when a promotion closed: the value, at purchase price, of what was left. */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "recording_loss_expiration")
public class RecordingLossExpiration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "promotion_id")
    @NotNull
    private Integer promotionId;

    @Column(name = "branch_id")
    @NotNull
    private Integer branchId;

    @Column(name = "total_loss")
    @NotNull
    private Double totalLoss;

    @Column(name = "creation_date")
    @NotNull
    private LocalDate creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id", insertable = false, updatable = false)
    private Promotion promotion;

    public RecordingLossExpiration(Integer promotionId, Integer branchId, Double totalLoss, LocalDate creationDate) {
        this.promotionId = promotionId;
        this.branchId = branchId;
        this.totalLoss = totalLoss;
        this.creationDate = creationDate;
    }
}
