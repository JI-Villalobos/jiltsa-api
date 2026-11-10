package com.jiltsa.admin.seller.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/** The employee record of a seller (one per seller). See V11__create_seller_profiles.sql. */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "seller_profile")
public class SellerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "seller_id")
    @NotNull
    private Integer sellerId;

    @Column(name = "full_name")
    @NotNull
    private String fullName;

    @Column(name = "birth_date")
    @NotNull
    private LocalDate birthDate;

    @NotNull
    private String phone;

    private String rfc;

    private String curp;

    @Column(name = "emergency_contact_name")
    @NotNull
    private String emergencyContactName;

    @Column(name = "emergency_contact_phone")
    @NotNull
    private String emergencyContactPhone;

    @Column(name = "hire_date")
    @NotNull
    private LocalDate hireDate;

    @NotNull
    private String position;

    @NotNull
    private Double salary;

    @Column(name = "privacy_notice_accepted")
    @NotNull
    private Boolean privacyNoticeAccepted;

    @Column(name = "privacy_notice_accepted_at")
    @NotNull
    private LocalDateTime privacyNoticeAcceptedAt;

    /** Email of the account that sent the form: the seller's own, or an admin's. */
    @Column(name = "registered_by")
    @NotNull
    private String registeredBy;

    @Column(name = "created_at")
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @NotNull
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", insertable = false, updatable = false)
    private Seller seller;
}
