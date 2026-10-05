package com.jiltsa.admin.cashproof.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CreateCashWithdrawalDto {
    private Integer id;
    @NotNull @Positive
    private Double amount;
    @NotBlank
    private String concept;
    @NotBlank
    private String sellerName;
    @NotNull
    private Integer branchId;
    @Schema(description = "Branch name", accessMode = Schema.AccessMode.READ_ONLY)
    private String branch;
}
