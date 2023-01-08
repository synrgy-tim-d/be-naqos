package com.binar.kelompokd.models.entity;

import com.binar.kelompokd.models.DateModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "t_setup_kost_specification")
public class KostSpecification extends DateModel implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String specificationDetails;

  @NotNull
  private Boolean isActive;

  @OneToOne(mappedBy = "specificationId")
  private Kost kost;
}
