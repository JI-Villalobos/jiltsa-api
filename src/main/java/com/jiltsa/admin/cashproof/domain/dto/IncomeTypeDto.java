package com.jiltsa.admin.cashproof.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class IncomeTypeDto {
    private Integer id;
    @NotBlank
    private String type;

}
