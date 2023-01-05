package com.binar.kelompokd.models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KostProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private String street;

    private Boolean isAvailable;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "t_kost_facilities",
            joinColumns = @JoinColumn(name ="kost_id"),
            inverseJoinColumns = @JoinColumn(name = "facilities_id")
    )
    private Set<KostFacility> kostFacilities = new HashSet<>();

    @OneToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "specification_id"
    )
    private KostSpecification kostSpecification;

    @OneToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "type_id"
    )
    private KostType kostType;

    @ManyToOne
    private Subcity location;

    @ManyToOne
    private PricesCategory priceCategory;
}
