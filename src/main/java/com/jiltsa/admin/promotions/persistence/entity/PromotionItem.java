package com.jiltsa.admin.promotions.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * One medicine within a promotion. {@code quantity} is what is still on the shelf; when the
 * promotion closes, quantity x purchasePrice is the loss written off for this item.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "promotion_item")
public class PromotionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "key")
    @NotNull
    private String key;

    private String description;

    @NotNull
    private Integer quantity;

    @Column(name = "normal_price")
    @NotNull
    private Double normalPrice;

    @Column(name = "promotion_price")
    @NotNull
    private Double promotionPrice;

    @Column(name = "purchase_price")
    @NotNull
    private Double purchasePrice;

    @Column(name = "promotion_id")
    @NotNull
    private Integer promotionId;

    @Column(name = "expiration_date")
    @NotNull
    private LocalDate expirationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id", insertable = false, updatable = false)
    private Promotion promotion;

    public PromotionItem(String key, String description, Integer quantity, Double normalPrice, Double promotionPrice,
                         Double purchasePrice, Integer promotionId, LocalDate expirationDate) {
        this.key = key;
        this.description = description;
        this.quantity = quantity;
        this.normalPrice = normalPrice;
        this.promotionPrice = promotionPrice;
        this.purchasePrice = purchasePrice;
        this.promotionId = promotionId;
        this.expirationDate = expirationDate;
    }

    /** What is lost if this item is not sold before it expires. */
    public double loss() {
        return quantity * purchasePrice;
    }
}
