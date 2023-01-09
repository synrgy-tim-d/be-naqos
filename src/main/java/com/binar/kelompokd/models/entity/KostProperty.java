package com.binar.kelompokd.models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "t_kost_property"
)
public class KostProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String street;

//    @Column(nullable = false)
    private Boolean isAvailable;

//    @ElementCollection
//    Set<String> photos;

    @ManyToOne(cascade = CascadeType.ALL)
    private KostPhoto photo;

    @Column(nullable = false)
    private Double pricePerCategory;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "t_kost_facilities",
            joinColumns = @JoinColumn(name ="kost_id"),
            inverseJoinColumns = @JoinColumn(name = "facilities_id")
    )
    private Set<KostFacility> kostFacilities = new HashSet<>();

    @OneToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "specification_id"
    )
    private KostSpecification kostSpecification;

    @OneToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "type_id"
    )
    private KostType kostType;

    @OneToOne
    private KostLocation location;

    @ManyToOne
    private PricesCategory priceCategory;
}
