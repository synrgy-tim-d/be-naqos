package com.binar.kelompokd.models.entity;

import com.binar.kelompokd.models.DateModel;
import com.binar.kelompokd.models.entity.kost.Kost;
import com.binar.kelompokd.models.entity.oauth.Users;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Table(name = "t_user_wishlist", uniqueConstraints = {
    @UniqueConstraint(columnNames = "id")
})
public class KostWishlist extends DateModel implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @OneToOne(targetEntity = Users.class, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", nullable = false)
  private Users userId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "kost_id")
  private Kost kostId;

  public KostWishlist(Users userId, Kost kostId){
    this.userId = userId;
    this.kostId = kostId;
  }
}
