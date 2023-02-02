package com.binar.kelompokd.models.entity.kost;

import com.binar.kelompokd.models.entity.oauth.Users;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_kost_review")
public class KostReview implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer Id;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "kost_id", referencedColumnName = "id")
  @JsonBackReference
  private Kost kostId;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  @JsonManagedReference
  private Users userId;

  @Column(nullable = false, length = 1)
  @Min(0)
  @Max(5)
  private Integer rating;

  @Column(nullable = false, name = "review_text")
  private String reviewText;

  @Column(nullable = false, name = "review_date")
  private Date reviewDate;

}
