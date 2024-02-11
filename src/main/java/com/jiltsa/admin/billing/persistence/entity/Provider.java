package com.jiltsa.admin.billing.persistence.entity;

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
@Table(name = "providers")
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String rfc;

    @OneToMany(mappedBy = "provider")
    private List<Bill> bills;

    public Provider(String name, String rfc) {
        this.name = name;
        this.rfc = rfc;
    }

    public Provider(Integer id, String name, String rfc) {
        this.id = id;
        this.name = name;
        this.rfc = rfc;
    }
}
