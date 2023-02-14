package com.binar.kelompokd.models.entity.kost;

import com.binar.kelompokd.enums.KostType;
import com.binar.kelompokd.models.DateModel;
import com.binar.kelompokd.models.entity.Image;
import com.binar.kelompokd.models.entity.location.City;
import com.binar.kelompokd.models.entity.oauth.Users;
import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

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

  @Schema(example = "150000")
  @Column(name = "price_per_daily")
  private BigDecimal pricePerDaily;   // saya tidak tau gmn caranya buat tipe data decimal di postgres. tapi berdasarkan riset kita bisa menggunakan bigdecimal untuk tipe data decimal

  @Schema(example = "650000")
  @Column(name = "price_per_weekly")
  private BigDecimal pricePerWeekly;

  @Schema(example = "2000000")
  @Column(nullable = false, name = "price_per_monthly")
  private BigDecimal pricePerMonthly;

  @Column(name = "kost_rating", length = 2, precision = 2)
  private double kostRating = 0;

  @Schema(example = "Jangan berisik di atas jam 10 malam")
  @Column(columnDefinition = "TEXT")
  private String rules;

  @Column(name = "f_question_1")
  private String question1;

  @Column(name = "f_answer_1")
  private String answer1;

  @Column(name = "f_question_2")
  private String question2;

  @Column(name = "f_answer_2")
  private String answer2;

  @Column(name = "f_question_3")
  private String question3;

  @Column(name = "f_answer_3")
  private String answer3;

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
      cascade = {javax.persistence.CascadeType.MERGE, javax.persistence.CascadeType.ALL})
  @JsonManagedReference
  private List<Room> rooms = new ArrayList<>();

  @OneToMany(
      mappedBy = "kosts",
      cascade = javax.persistence.CascadeType.MERGE,
      orphanRemoval = true)
  @JsonManagedReference
  private List<Image> imageKosts = new ArrayList<>();

  @JsonIgnore
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "kostId",  cascade = javax.persistence.CascadeType.ALL,
          orphanRemoval = true)
  private List<KostWishlist> wishlists;

  @ManyToMany(fetch = FetchType.LAZY,
          cascade = {
                  javax.persistence.CascadeType.PERSIST,
                  javax.persistence.CascadeType.MERGE
          })
  @JoinTable(name = "t_kost_facility",
          joinColumns = { @JoinColumn(name = "kost_id") },
          inverseJoinColumns = { @JoinColumn(name = "facility_id") })
  private Set<Facility> facilities = new HashSet<>();

  public void addImageKost(Image imageKost) {
    imageKosts.add(imageKost);
    imageKost.setKosts(this);
  }

  public void deleteImageKost(Image imageKost) {
    imageKosts.remove(imageKost);
    imageKost.setKosts(null);
  }

  public void addFacility(Facility facility) {
    this.facilities.add(facility);
    facility.getKosts().add(this);
  }

  public void removeFacility(UUID facilityId) {
    Facility facility = this.facilities.stream().filter(f -> f.getId().toString().equals(facilityId.toString())).findFirst().orElse(null);
    if (facility != null) {
      this.facilities.remove(facility);
      facility.getKosts().remove(this);
    }
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
