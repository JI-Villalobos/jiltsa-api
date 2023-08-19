package com.jiltsa.admin.seller.persistence.entity;

import com.jiltsa.admin.branch.persistence.entity.Branch;
import com.jiltsa.admin.cashproof.persistence.entity.Accounting;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seller")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "full_name")
    @NotNull
    private String fullName;
    @Column(name = "branch_id")
    @NotNull
    private Integer branchId;
    private String password = "1234";
    @NotNull
    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "branch_id", insertable = false, updatable = false)
    private Branch branch;

    @OneToMany(mappedBy = "seller")
    private List<Accounting> accounting;

    public Seller(String fullName, Integer branchId, String password, Boolean isActive) {
        this.fullName = fullName;
        this.branchId = branchId;
        this.password = password;
        this.isActive = isActive;
    }
}




