package com.binar.kelompokd.models.entity.location;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder    // builder ini untuk set value saat object creation
@Table(name = "t_setup_address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(example = "-6")  // untuk set example di swagger
    private Double latitude;

    @Schema(example = "106")  // untuk set example di swagger
    private Double longitude;

    @Schema(example = "Disctrict A")  // untuk set example di swagger
    @Column(nullable = false, length = 50)
    private String district;

    @Schema(example = "Subdistrict B")  // untuk set example di swagger
    @Column(nullable = false, length = 50)
    private String subdistrict;

    @Schema(example = "Jl. Kabupaten, Nusupan, Trihanggo, Gamping, Sleman Regency")  // untuk set example di swagger
    @Column(nullable = false, length = 100)
    private String address;

    @Schema(example = "55291")
    @Column(nullable = false, length = 10)
    private String postalCode;

//    @OneToOne
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
//    private Province province;

    @Schema(example = "1")
    private Integer provinceId;     // tipe data menyesuaikan erd, relasi belum terbuat

//    @OneToOne
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
//    private City city;

    @Schema(example = "1")
    private Integer cityId; // tipe data menyesuaikan erd. relasi belum terbuat
}