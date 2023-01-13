package com.binar.kelompokd.models.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
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
