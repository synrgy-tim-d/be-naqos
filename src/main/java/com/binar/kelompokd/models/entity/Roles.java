package com.binar.kelompokd.models.entity;

import com.binar.kelompokd.enums.ERole;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "t_roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @NotNull
    private ERole name;

    @NotNull
    private Boolean isActive = false;
}