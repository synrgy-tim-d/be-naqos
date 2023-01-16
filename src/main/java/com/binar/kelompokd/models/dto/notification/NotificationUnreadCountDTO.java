package com.binar.kelompokd.models.dto.notification;

import com.binar.kelompokd.models.entity.oauth.Users;
import lombok.Data;

@Data
public class NotificationUnreadCountDTO {
  private Long userId;
  private String name;
  private String email;
  private Integer unread;

  public NotificationUnreadCountDTO(Users user, Integer unread) {
    this.userId = user.getId();
    this.name = user.getFullname();
    this.email = user.getUsername();
    this.unread = unread;
  }
}
