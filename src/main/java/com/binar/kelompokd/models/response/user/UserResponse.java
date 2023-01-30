package com.binar.kelompokd.models.response.user;

import com.binar.kelompokd.models.entity.oauth.Users;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
  private Long id;
  private String username;
  private String fullname;
  private String phoneNumber;
  private String imgUrl;

  public UserResponse(Users users) {
    this.id = users.getId();
    this.username = users.getUsername();
    this.fullname = users.getFullname();
    this.phoneNumber = users.getPhoneNumber();
    this.imgUrl = users.getImgUrl();
  }

}
