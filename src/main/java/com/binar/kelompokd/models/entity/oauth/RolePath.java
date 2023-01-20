package com.binar.kelompokd.models.entity.oauth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "oauth_role_path")
public class RolePath implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 50)
  private String name;

  private String pattern;

  private String method;

  @ManyToOne(targetEntity = Roles.class, cascade = CascadeType.ALL)
  @JsonIgnore
  @JsonManagedReference
  private Roles roles;

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

  public String getPattern() {
    return pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public Roles getRole() {
    return roles;
  }

  public void setRole(Roles roles) {
    this.roles = roles;
  }
}
