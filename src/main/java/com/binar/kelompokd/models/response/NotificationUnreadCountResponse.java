package com.binar.kelompokd.models.response;

import com.binar.kelompokd.models.entity.oauth.Users;
import lombok.Data;

@Data
public class NotificationUnreadCountResponse {
  private Long userId;
  private String name;
  private String email;
  private Integer unread;
  public NotificationUnreadCountResponse(Users users, Integer unread) {
    this.userId = users.getId();
    this.name = users.getFullname();
    this.email = users.getUsername();
    this.unread = unread;
  }

}
