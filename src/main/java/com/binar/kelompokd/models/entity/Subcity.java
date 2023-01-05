package com.binar.kelompokd.models.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "t_setup_subcities"
)
public class Subcity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private City cities;
}
