package com.jiltsa.admin.branch.domain.dto;

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
    private String name;
    private Boolean isActive;
}
