package com.binar.kelompokd.models.request;

import com.binar.kelompokd.enums.Condition;
import com.binar.kelompokd.enums.KostType;
import com.binar.kelompokd.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KostRequest {
    @Column(length = 100)
    @NotNull
    private String name;

    @NotNull
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    private KostType kostType;

    @NotNull
    private Boolean is_available;

    private Double latitude;

    private Double longitude;

    @NotNull
    @Column(length = 50)
    private String district;

    @NotNull
    @Column(length = 50)
    private String subdistrict;

    @NotNull
    @Column(length = 100)
    private String address;

    @NotNull
    @Column(length = 10)
    private String postalCode;

    @NotNull
    @Column(length = 50)
    private String province;

    @NotNull
    @Column(length = 50)
    private String city;

    private Integer[] roomId;

    // 	  location_id: int  ??
}
