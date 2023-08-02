package com.jiltsa.admin.branch.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchConfigurationDto {
    private Integer id;
    private Integer branchId;
    private Double initialBalance;
}
