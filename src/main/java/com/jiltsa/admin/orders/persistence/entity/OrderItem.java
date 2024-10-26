package com.jiltsa.admin.orders.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
    @JoinColumn(name = "orderId", insertable = false, updatable = false)
    private Order order;

    public @NotNull Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(@NotNull Integer orderId) {
        this.orderId = orderId;
    }

    public @NotNull String getItem() {
        return item;
    }

    public void setItem(@NotNull String item) {
        this.item = item;
    }

    public @NotNull Integer getRequested() {
        return requested;
    }

    public void setRequested(@NotNull Integer requested) {
        this.requested = requested;
    }

    public @NotNull Double getPrice() {
        return price;
    }

    public void setPrice(@NotNull Double price) {
        this.price = price;
    }

    public @NotNull Double getBudgeted() {
        return budgeted;
    }

    public void setBudgeted(@NotNull Double budgeted) {
        this.budgeted = budgeted;
    }

    public @NotNull String getItemType() {
        return itemType;
    }

    public void setItemType(@NotNull String itemType) {
        this.itemType = itemType;
    }

    public @NotNull Integer getStocked() {
        return stocked;
    }

    public void setStocked(@NotNull Integer stocked) {
        this.stocked = stocked;
    }

    public @NotNull Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(@NotNull Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public @NotNull Double getTotal() {
        return total;
    }

    public void setTotal(@NotNull Double total) {
        this.total = total;
    }

    public @NotNull Integer getStatus() {
        return status;
    }

    public void setStatus(@NotNull Integer status) {
        this.status = status;
    }

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

    public OrderItem(Integer id, Integer orderId, String item, Integer requested, Double price, Double budgeted,
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
