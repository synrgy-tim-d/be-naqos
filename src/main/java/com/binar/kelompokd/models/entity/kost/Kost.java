package com.binar.kelompokd.models.entity.kost;

import com.binar.kelompokd.enums.KostType;
import com.binar.kelompokd.models.DateModel;
//import com.binar.kelompokd.models.entity.oauth.Users;
import com.binar.kelompokd.models.entity.Image;
import com.binar.kelompokd.models.entity.KostWishlist;
import com.binar.kelompokd.models.entity.location.City;
import com.binar.kelompokd.models.entity.oauth.Users;
import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_kost")
public class Kost extends DateModel implements Serializable {
  @Id
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

  @Schema(example = "-6")
  @Column(precision = 10)// untuk set example di swagger
  private Double latitude;

  @Schema(example = "106")  // untuk set example di swagger
  @Column(precision = 10)
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
  @Column(nullable = false, length = 10, name = "postal_code")
  private String postalCode;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "owner_id", referencedColumnName = "id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Users ownerId;

  @ManyToOne
  @JoinColumn(name="city_id", referencedColumnName = "id")
  @Cascade(org.hibernate.annotations.CascadeType.MERGE)
  private City city;

  @OneToMany(
      mappedBy = "kost",
      cascade = javax.persistence.CascadeType.MERGE)
  @JsonManagedReference
  @OnDelete(action = OnDeleteAction.CASCADE)
  private List<Room> rooms = new ArrayList<>();

  @OneToMany(
      mappedBy = "kosts",
      cascade = javax.persistence.CascadeType.MERGE,
      orphanRemoval = true)
  @JsonManagedReference
  private List<Image> imageKosts = new ArrayList<>();

  @JsonIgnore
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "kostId")
  private List<KostWishlist> wishlists;

  public void add(Image imageKost) {
    imageKosts.add(imageKost);
    imageKost.setKosts(this);
  }

  public void deleteImageKost(Image imageKost) {
    imageKosts.remove(imageKost);
    imageKost.setKosts(null);
  }

  @Override
  public String toString() {
    return "Kost{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", kostType=" + kostType +
            ", isAvailable=" + isAvailable +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            ", address='" + address + '\'' +
            ", district='" + district + '\'' +
            ", subdistrict='" + subdistrict + '\'' +
            ", postalCode='" + postalCode + '\'' +
            ", ownerId=" + ownerId +
            ", city=" + city +
            '}';
  }
}
