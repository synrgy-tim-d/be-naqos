package com.binar.kelompokd.models.dto.notification;

import com.binar.kelompokd.models.entity.oauth.Users;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class InsertNotificationDTO {
  @NotEmpty(message = "title is required.")
  private String title;

  @NotEmpty(message = "content is required.")
  private String content;

  @NotEmpty(message = "isRead is required.")
  private Boolean isRead = false;

  @NotEmpty(message = "sentTo is required.")
  private Users sentTo;
}
