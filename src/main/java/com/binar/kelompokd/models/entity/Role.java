package com.binar.kelompokd.models.entity;

import com.binar.kelompokd.enums.ERole;
import com.binar.kelompokd.models.DateModel;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@Table(name = "t_roles")
public class Role extends DateModel implements Serializable {
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