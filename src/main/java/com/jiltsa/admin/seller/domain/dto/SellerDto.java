package com.jiltsa.admin.seller.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank
    private String fullName;
    @NotNull
    private Integer branchId;
    @NotNull
    private Boolean isActive;
    @Size(max = 255)
    private String userTurn;
}
