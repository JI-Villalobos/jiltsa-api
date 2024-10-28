package com.jiltsa.admin.orders.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    @NotNull
    private Integer orderId;

    @NotNull
    private String item;

    @NotNull
    private Integer requested;

    @NotNull
    private Double price;

    @NotNull
    private Double budgeted;

    @Column(name = "item_type")
    @NotNull
    private String itemType;

    @NotNull
    private Integer stocked;

    @Column(name = "final_price")
    @NotNull
    private Double finalPrice;

    @NotNull
    private Double total;

    @NotNull
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Order order;


    public OrderItem(Integer orderId, String item, Integer requested, Double price, Double budgeted,
                     String itemType, Integer stocked, Double finalPrice, Double total, Integer status) {
        this.orderId = orderId;
        this.item = item;
        this.requested = requested;
        this.price = price;
        this.budgeted = budgeted;
        this.itemType = itemType;
        this.stocked = stocked;
        this.finalPrice = finalPrice;
        this.total = total;
        this.status = status;
    }

    public OrderItem(Long id, Integer orderId, String item, Integer requested, Double price, Double budgeted,
                     String itemType, Integer stocked, Double finalPrice, Double total, Integer status) {
        this.id = id;
        this.orderId = orderId;
        this.item = item;
        this.requested = requested;
        this.price = price;
        this.budgeted = budgeted;
        this.itemType = itemType;
        this.stocked = stocked;
        this.finalPrice = finalPrice;
        this.total = total;
        this.status = status;
    }
}
