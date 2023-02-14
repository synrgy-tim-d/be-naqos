package com.binar.kelompokd.models.request.kost;

import com.binar.kelompokd.enums.Condition;
import com.binar.kelompokd.enums.KostType;
import com.binar.kelompokd.enums.RoomType;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "Kost Alamanda")
    private String name;

    @NotNull
    @Schema(example = "Kos untuk siswa SYNRGY")
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Schema(example = "KOS_CAMPURAN")
    private KostType kost_type;

    @NotNull
    @Schema(example = "true")
    private Boolean is_available;

    private Double latitude;

    private Double longitude;

    @Column(length = 100)
    private String address;

    @Column(length = 50)
    private String district;

    @Column(length = 50)
    private String subdistrict;

    @Column(length = 10)
    private String postalCode;

    @Column(length = 50)
    private String province;

    @Column(length = 50)
    private String city;
    // 	  location_id: int  ??

    @NotNull
    @Schema(example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID owner_id;

    @NotNull
    @Schema(example = "1")
    private Integer location_id;

    @NotNull
    @Schema(example = "[\"123e4567-e89b-12d3-a456-426614174000\"]")
    private UUID[] room_id; // seperti yg bisa dilihat, kita harus memasukan room id secara manual karena tipe data di erd adalah array. oleh karena itu, untuk membuat kos, kita perlu membuat room terlebih dahulu.


}
