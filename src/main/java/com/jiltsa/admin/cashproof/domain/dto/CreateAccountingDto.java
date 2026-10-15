package com.jiltsa.admin.cashproof.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CreateAccountingDto {
    private Integer id;
    @NotNull
    private Integer sellerId;
    @NotNull
    private Integer branchId;
    private LocalDateTime date;
}
