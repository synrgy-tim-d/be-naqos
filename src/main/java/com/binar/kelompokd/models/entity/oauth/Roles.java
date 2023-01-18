package com.binar.kelompokd.models.entity.oauth;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
@Table(
    name = "oauth_role",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "role_name_and_type",
            columnNames = {"type", "name"}
        )
    }
)
public class Roles implements GrantedAuthority {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 20)
  private String name;

  private String type;

  @OneToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonBackReference
  private List<RolePath> rolePaths;

  @JsonIgnore
  @ManyToMany(targetEntity = Users.class, mappedBy = "roles", fetch = FetchType.LAZY)
  private List<Users> users;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  @JsonIgnore
  public String getAuthority() {
    return this.name;
  }

  public List<RolePath> getRolePaths() {
    return rolePaths;
  }

  public void setRolePaths(List<RolePath> rolePaths) {
    this.rolePaths = rolePaths;
  }

  public List<Users> getUsers() {
    return users;
  }

  public void setUsers(List<Users> users) {
    this.users = users;
  }
}