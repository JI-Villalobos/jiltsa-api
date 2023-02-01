package com.jiltsa.admin.cashproof.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateAccountingDto {
    private Integer id;
    private Integer sellerId;
    private Integer branchId;
}
