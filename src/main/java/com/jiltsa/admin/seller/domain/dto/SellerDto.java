package com.jiltsa.admin.seller.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SellerDto {
    private Integer id;
    @NotNull
    private String fullName;
    @NotNull
    private Integer branchId;
    @NotNull
    private Boolean isActive;
}
