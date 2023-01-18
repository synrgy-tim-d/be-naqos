package com.binar.kelompokd.models.entity;

import com.binar.kelompokd.enums.KostType;
import com.binar.kelompokd.models.DateModel;
//import com.binar.kelompokd.models.entity.oauth.Users;
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
import java.io.Serializable;
import java.util.List;
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

  // tambah owner id here, sengaja belum dibuat karena tabel user belum jadi
  // owner id int

//  @OneToOne
//  @Cascade(CascadeType.ALL)
//  private Address location;

  @Schema(example = "1")
  private Integer locationId;

  // room id array[]
  @Schema(example = "[\"123e4567-e89b-12d3-a456-426614174000\"]")
  private UUID[] roomId;  // tipe data menyesuaikan erd, relasi belum terbuat
}
