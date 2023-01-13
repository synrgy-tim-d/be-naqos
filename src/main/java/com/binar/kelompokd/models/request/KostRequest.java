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
import java.util.UUID;

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
    private KostType kost_type;

    @NotNull
    private Boolean is_available;

    @NotNull
    private Integer owner_id;

    @NotNull
    private Integer location_id;

    @NotNull
    private UUID[] room_id;

    private Double latitude;

    private Double longitude;

    @Column(length = 50)
    private String district;

    @Column(length = 50)
    private String subdistrict;

    @Column(length = 100)
    private String address;

    @Column(length = 10)
    private String postalCode;

    @Column(length = 50)
    private String province;

    @Column(length = 50)
    private String city;
    // 	  location_id: int  ??
}
