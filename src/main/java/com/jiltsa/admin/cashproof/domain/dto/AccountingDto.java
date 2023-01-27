package com.jiltsa.admin.cashproof.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class AccountingDto {
    private Integer id;
    private Integer sellerId;
    private Integer branchId;
    private LocalDateTime date;
}
