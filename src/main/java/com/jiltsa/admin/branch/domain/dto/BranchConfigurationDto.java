package com.jiltsa.admin.branch.domain.dto;

import com.jiltsa.admin.branch.persistence.entity.Profile;
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
    private Boolean canEditAccount;
    private Boolean canOpenOutdatedAccount;
    private Profile profile;
}
