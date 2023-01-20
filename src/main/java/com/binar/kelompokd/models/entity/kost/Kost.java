package com.binar.kelompokd.models.entity.kost;

import com.binar.kelompokd.enums.KostType;
import com.binar.kelompokd.models.DateModel;
//import com.binar.kelompokd.models.entity.oauth.Users;
import com.binar.kelompokd.models.entity.Image;
import com.binar.kelompokd.models.entity.location.City;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "t_kost")
public class Kost extends DateModel implements Serializable {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
          name = "UUID",
          strategy = "org.hibernate.id.UUIDGenerator",
          parameters = {
                  @Parameter(
                          name = "uuid_gen_strategy_class",
                          value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                  )
          }
  )
  @Cascade(CascadeType.ALL)
  private UUID id;

  @Schema(example = "Kost Alamanda")
  @Column(length = 100, nullable = false)
  private String name;

  @Schema(example = "Kos untuk siswa SYNRGY")
  @Column(nullable = false)
  private String description;

  @Schema(example = "KOS_CAMPURAN")
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private KostType kostType;

  @Schema(example = "true")
  @Column(nullable = false)
  private Boolean isAvailable;

  @Schema(example = "-6")  // untuk set example di swagger
  private Double latitude;

  @Schema(example = "106")  // untuk set example di swagger
  private Double longitude;

  @Schema(example = "Jl. Kabupaten, Nusupan, Trihanggo, Gamping, Sleman Regency")  // untuk set example di swagger
  @Column(nullable = false, length = 100)
  private String address;

  @Schema(example = "Disctrict A")  // untuk set example di swagger
  @Column(nullable = false, length = 50)
  private String district;

  @Schema(example = "Subdistrict B")  // untuk set example di swagger
  @Column(nullable = false, length = 50)
  private String subdistrict;

  @Schema(example = "55291")
  @Column(nullable = false, length = 10)
  private String postalCode;

//    @OneToOne
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
//    private Province province;

//    @OneToOne
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
//    private City city;

  @ManyToOne
  @JoinColumn(name="city_id", referencedColumnName = "id")
  private City city;

  @OneToMany(mappedBy="kost")
  private Set<Image> images;


//  @OneToOne
//  @Cascade(CascadeType.ALL)
//  private Address location;

}
