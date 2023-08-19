package com.jiltsa.admin.cashproof.persistence.entity;

import com.jiltsa.admin.branch.persistence.entity.Branch;
import com.jiltsa.admin.seller.persistence.entity.Seller;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Accounting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "seller_id")
    @NotNull
    private Integer sellerId;
    @Column(name = "branch_id")
    @NotNull
    private Integer branchId;
    private LocalDateTime date = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "branch_id", updatable = false, insertable = false)
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "seller_id", updatable = false, insertable = false)
    private Seller seller;

    @OneToMany(mappedBy = "accounting")
    private List<IncomeRegistry> incomeRegistries;

    @OneToMany(mappedBy = "accounting")
    private List<ExpenseRegistry> expenseRegistries;

    public Accounting(Integer sellerId, Integer branchId, LocalDateTime date) {
        this.sellerId = sellerId;
        this.branchId = branchId;
        this.date = date;
    }
}
