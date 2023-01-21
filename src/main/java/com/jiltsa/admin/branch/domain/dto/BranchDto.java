package com.jiltsa.admin.branch.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchDto {
    private Integer id;
    private String name;
    private Boolean isActive;
}
