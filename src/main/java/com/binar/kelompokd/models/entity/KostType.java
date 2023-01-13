package com.binar.kelompokd.models.entity;

import com.binar.kelompokd.models.DateModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "t_setup_kost_types")
public class KostType extends DateModel implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String typeName;

  @OneToOne(mappedBy = "typeId")
  private Kost kost;
}
