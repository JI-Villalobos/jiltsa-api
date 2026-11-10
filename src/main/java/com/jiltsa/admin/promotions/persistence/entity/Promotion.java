package com.jiltsa.admin.promotions.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/** A promotion of medicine that is about to expire, run by one branch until {@code promotionEndDate}. */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "promotion")
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "promotion_name")
    @NotNull
    private String promotionName;

    @Column(name = "branch_id")
    @NotNull
    private Integer branchId;

    @Column(name = "creation_date")
    @NotNull
    private LocalDate creationDate;

    @Column(name = "promotion_end_date")
    @NotNull
    private LocalDate promotionEndDate;

    @NotNull
    private Boolean active;

    @OneToMany(mappedBy = "promotion")
    private List<PromotionItem> items;

    public Promotion(String promotionName, Integer branchId, LocalDate creationDate, LocalDate promotionEndDate, Boolean active) {
        this.promotionName = promotionName;
        this.branchId = branchId;
        this.creationDate = creationDate;
        this.promotionEndDate = promotionEndDate;
        this.active = active;
    }
}
