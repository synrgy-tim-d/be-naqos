package com.binar.kelompokd.models.entity.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;

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

    @Column(nullable = false)
    private Boolean isAvailable;

    Map<String, String> photos;

    @Column(nullable = false)
    private Double pricePerCategory;

    private Integer kostSpecificationId;

    private Integer kostTypeId;

    private Integer locationId;

    private Integer priceCategoryId;

    private Integer kostFacilityId;

}
