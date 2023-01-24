package com.jiltsa.admin.seller.domain.dto;

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
    private String fullName;
    private Integer branchId;
    private Boolean isActive;
}
