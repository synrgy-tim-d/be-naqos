package com.binar.kelompokd.models.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KostPropertyRequest {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String street;

//    @Column(nullable = false)
    @JsonIgnore
    private Boolean isAvailable = false;

    private String[] kostPhotos;

    @Column(nullable = false)
    private Double pricePerCategory;

    private Integer kostSpecificationId;

    private Integer kostTypeId;

    private Integer locationId;

    private Integer priceCategoryId;

    private Integer kostFacilityId;

}
