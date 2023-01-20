package com.binar.kelompokd.models.entity.location;

import com.binar.kelompokd.models.DateModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "t_setup_city")
public class City {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Schema(example = "Yogyakarta")
  @Column(length = 50, nullable = false)
  private String city;

  @ManyToOne()
  @JoinColumn(name="province_id", referencedColumnName = "id")
  @Cascade({org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.MERGE})
  private Province province;
}
