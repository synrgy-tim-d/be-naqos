package com.binar.kelompokd.models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "t_kost_property")
public class Kost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @NotNull
    private String name;

    @NotNull
    private String description;

    private String street;

    @NotNull
    private Double pricePerCategory;

    @NotNull
    private Boolean isAvailable;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "specification_id", referencedColumnName = "id")
    private KostSpecification specificationId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "facility_id", referencedColumnName = "id")
    private KostFacility facilityId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private KostType typeId;

    @ManyToOne
    private Subcity location;

    @ManyToOne
    private PriceCategory priceCategory;
}
