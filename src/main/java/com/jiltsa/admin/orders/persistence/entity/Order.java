package com.jiltsa.admin.orders.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "provider_id")
    @NotNull
    private Integer providerId;

    @Column(name = "branch_id")
    @NotNull
    private Integer branchId;

    @Column(name = "creation_date")
    @NotNull
    private Date creationDate;

    @Column(name = "estimated_cost")
    @NotNull
    private Double estimatedCost;

    @Column(name = "real_cost")
    @NotNull
    private Double realCost;

    @Column(name = "status")
    @NotNull
    private Integer status;

    @Column(name = "is_open")
    @NotNull
    private Boolean isOpen;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items;

    public @NotNull Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(@NotNull Integer providerId) {
        this.providerId = providerId;
    }

    public @NotNull Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(@NotNull Integer branchId) {
        this.branchId = branchId;
    }

    public @NotNull Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(@NotNull Date creationDate) {
        this.creationDate = creationDate;
    }

    public @NotNull Double getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(@NotNull Double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }


    public @NotNull Integer getStatus() {
        return status;
    }

    public @NotNull Boolean getOpen() {
        return isOpen;
    }

    public void setStatus(@NotNull Integer status) {
        this.status = status;
    }

    public @NotNull Double getRealCost() {
        return realCost;
    }

    public void setRealCost(@NotNull Double realCost) {
        this.realCost = realCost;
    }

    public void setOpen(@NotNull Boolean open) {
        isOpen = open;
    }

    public Order(Integer providerId, Integer branchId, Date creationDate, Double estimatedCost, Double realCost,
                 Integer status, Boolean isOpen) {
        this.providerId = providerId;
        this.branchId = branchId;
        this.creationDate = creationDate;
        this.estimatedCost = estimatedCost;
        this.realCost = realCost;
        this.status = status;
        this.isOpen = isOpen;
    }

    public Order(Integer id, Integer providerId, Integer branchId, Date creationDate, Double estimatedCost, Double realCost,
                 Integer status, Boolean isOpen) {
        this.id = id;
        this.providerId = providerId;
        this.branchId = branchId;
        this.creationDate = creationDate;
        this.estimatedCost = estimatedCost;
        this.realCost = realCost;
        this.status = status;
        this.isOpen = isOpen;
    }

    public Order(Integer id, Integer providerId, Integer branchId, Date creationDate, Double estimatedCost, Double realCost,
                 Integer status, Boolean isOpen, List<OrderItem> items) {
        this.id = id;
        this.providerId = providerId;
        this.branchId = branchId;
        this.creationDate = creationDate;
        this.estimatedCost = estimatedCost;
        this.realCost = realCost;
        this.status = status;
        this.isOpen = isOpen;
        this.items = items;
    }

    public List<OrderItem> getItems() {
        return items;
    }
}
