package com.jiltsa.admin.branch.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "branch_config")
public class BranchConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "branch_id")
    private Integer branchId;
    @NotNull
    @Column(name = "initial_balance")
    private Double initialBalance;

    @NotNull
    @Column(name = "can_edit_account")
    private Boolean canEditAccount;

    @NotNull
    @Column(name = "can_open_outdated_account")
    private Boolean canOpenOutdatedAccount;

    @NotNull
    @Column(name = "profile")
    private Profile profile;

    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public BranchConfiguration(Integer branchId, Double initialBalance, Boolean canEditAccount,
                               Boolean canOpenOutdatedAccount, Profile profile, LocalDateTime modifiedDate) {
        this.branchId = branchId;
        this.initialBalance = initialBalance;
        this.canEditAccount = canEditAccount;
        this.canOpenOutdatedAccount = canOpenOutdatedAccount;
        this.profile = profile;
        this.modifiedDate = modifiedDate;
    }
}
