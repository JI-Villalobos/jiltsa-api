package com.jiltsa.admin.seller.domain.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * The "Información de Empleados" form plus the seller it belongs to. {@code privacyNoticeAccepted}
 * must be true: the seller accepts the notice shown by the frontend, or an admin registers an
 * employee who already accepted it in the previous form. {@code id}, {@code branchId},
 * {@code privacyNoticeAcceptedAt}, {@code registeredBy}, {@code createdAt} and {@code updatedAt}
 * are read-only. RFC and CURP are optional (the form asks
 * for them only when known) and are stored upper-cased. The INE pictures and the address proof go
 * through POST /files/upload-url with ownerType SELLER_PROFILE and a documentType.
 */
public record SellerProfileDto(
        Integer id,
        @NotNull Integer sellerId,
        @NotBlank @Size(max = 255) String fullName,
        @NotNull @Past LocalDate birthDate,
        @NotBlank @Pattern(regexp = PHONE, message = "must be a phone number of 7 to 20 digits") String phone,
        @Pattern(regexp = RFC, message = "must be a valid RFC") String rfc,
        @Pattern(regexp = CURP, message = "must be a valid CURP") String curp,
        @NotBlank @Size(max = 255) String emergencyContactName,
        @NotBlank @Pattern(regexp = PHONE, message = "must be a phone number of 7 to 20 digits") String emergencyContactPhone,
        @NotNull @PastOrPresent LocalDate hireDate,
        @NotBlank @Size(max = 255) String position,
        @NotNull @PositiveOrZero Double salary,
        @NotNull @AssertTrue(message = "the privacy notice must be accepted") Boolean privacyNoticeAccepted,
        Integer branchId,
        LocalDateTime privacyNoticeAcceptedAt,
        String registeredBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    static final String PHONE = "^\\+?[0-9][0-9 ()-]{5,18}[0-9]$";
    static final String RFC = "^(?i)[A-ZÑ&]{3,4}[0-9]{6}[A-Z0-9]{3}$";
    static final String CURP = "^(?i)[A-Z]{4}[0-9]{6}[HM][A-Z]{5}[A-Z0-9][0-9]$";
}
