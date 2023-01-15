//package com.binar.kelompokd.models.response;
//
//import com.binar.kelompokd.models.entity.Notification;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.Date;
//
//@Data
//@NoArgsConstructor
//public class NotificationResponse {
//  private Integer id;
//  private String title;
//  private String content;
//  private Boolean isRead;
//  private Date lastUpdated;
//  private Integer userId;
//
//  public NotificationResponse(Notification notification) {
//    this.id = notification.getId();
//    this.title = notification.getTitle();
//    this.content = notification.getContent();
//    this.isRead = notification.getIsRead();
//    this.lastUpdated = notification.getUpdatedAt();
//    this.userId = notification.getSentTo().getId();
//  }
//}
