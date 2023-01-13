package com.binar.kelompokd.models.entity;

import com.binar.kelompokd.enums.KostType;
import com.binar.kelompokd.models.DateModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
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

  // owner id int

//  @OneToOne
//  @Cascade(CascadeType.ALL)
//  private Address location;

  private Integer locationId;

  // room id array[]
  private UUID[] roomId;

  @Column(length = 100, nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private KostType kostType;

  @Column(nullable = false)
  private Boolean isAvailable;
}
