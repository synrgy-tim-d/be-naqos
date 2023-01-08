package com.binar.kelompokd.models.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private Integer id;
  private String email;

  public JwtResponse(String token, Integer id, String email) {
    this.token = token;
    this.id = id;
    this.email = email;
  }
}
