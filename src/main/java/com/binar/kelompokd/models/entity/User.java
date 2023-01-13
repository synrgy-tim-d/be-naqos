package com.binar.kelompokd.models.entity;

import com.binar.kelompokd.enums.EGender;
import com.binar.kelompokd.models.DateModel;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "t_users")
@Table(
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "name")
    }
)
public class User extends DateModel implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @NotNull
  private String email;

  @NotNull
  private String password;

  @NotNull
  private String name;

  private String photoProfile;

  @NotNull
  @Enumerated(EnumType.STRING)
  private EGender gender;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable( name = "t_user_roles",
      joinColumns = @JoinColumn(name ="user_id"),
      inverseJoinColumns = @JoinColumn(name = "roles_id"))
  private Set<Role> roles = new HashSet<>();
}

