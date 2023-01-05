package com.binar.kelompokd.models.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "t_setup_kost_specification"
)
public class KostSpecification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String specificationDetails;

    @Column(nullable = false)
    private Boolean isActive;

}
