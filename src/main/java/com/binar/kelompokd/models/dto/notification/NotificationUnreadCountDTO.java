package com.binar.kelompokd.models.dto.notification;

import com.binar.kelompokd.models.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class NotificationUnreadCountDTO {
  private Integer userId;
  private String name;
  private String email;
  private Integer unread;

  public NotificationUnreadCountDTO(User user, Integer unread) {
    this.userId = user.getId();
    this.name = user.getName();
    this.email = user.getEmail();
    this.unread = unread;
  }
}
