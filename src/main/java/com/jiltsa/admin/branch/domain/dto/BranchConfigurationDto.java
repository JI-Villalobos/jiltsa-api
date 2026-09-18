package com.jiltsa.admin.branch.domain.dto;

import com.jiltsa.admin.branch.persistence.entity.Profile;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
    @NotNull @PositiveOrZero
    private Double initialBalance;
    @NotNull
    private Boolean canEditAccount;
    @NotNull
    private Boolean canOpenOutdatedAccount;
    @NotNull
    private Profile profile;
}
