package com.jiltsa.admin.branch.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchDto {
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private Boolean isActive;
}
