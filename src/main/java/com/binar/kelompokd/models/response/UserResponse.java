package com.binar.kelompokd.models.response;

import com.binar.kelompokd.models.entity.oauth.Users;
import lombok.Data;

@Data
public class UserResponse {
  private Long id;
  private String username;
  private String fullname;
  private String phoneNumber;

  public UserResponse(Users users) {
    this.id = users.getId();
    this.username = users.getUsername();
    this.fullname = users.getFullname();
    this.phoneNumber = users.getPhoneNumber();
  }

}
