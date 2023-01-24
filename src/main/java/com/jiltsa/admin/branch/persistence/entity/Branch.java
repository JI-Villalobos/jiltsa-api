package com.jiltsa.admin.branch.persistence.entity;

import com.jiltsa.admin.seller.persistence.entity.Seller;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "branches")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "branch")
    private List<Seller> sellers;

}
