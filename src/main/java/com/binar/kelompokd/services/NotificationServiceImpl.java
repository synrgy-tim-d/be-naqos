package com.binar.kelompokd.services;

import com.binar.kelompokd.interfaces.INotificationService;
import com.binar.kelompokd.interfaces.IUserService;
import com.binar.kelompokd.models.entity.Notification;
import com.binar.kelompokd.models.entity.User;
import com.binar.kelompokd.repos.NotificationRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Service
//@AllArgsConstructor
//public class NotificationServiceImpl implements INotificationService {
//
//  @Autowired
//  private NotificationRepo notificationRepo;
//
//  @Autowired
//  private IUserService iUserService;
//
//  @Override
//  public void saveNotification(String title, String content, Integer userId) {
//    Notification notification = new Notification();
//    User user = iUserService.findUsersById(userId);
//    notification.setTitle(title);
//    notification.setContent(content);
//    notification.setSentTo(user);
//    notificationRepo.save(notification);
//  }
//
//  @Override
//  public void updateIsRead(Integer id) {
//    Optional<Notification> notification = notificationRepo.findById(id);
//    notification.ifPresent(notification1 -> {
//      notification1.setIsRead(true);
//      notificationRepo.save(notification1);
//    });
//  }
//
//  @Override
//  public void markAllAsRead(Integer sentTo) {
//    List<Notification> notificationList = notificationRepo.getAllNotificationsWhereIsReadFalse(sentTo);
//
//    for (Notification notification : notificationList) {
//      notification.setIsRead(true);
//      notificationRepo.save(notification);
//    }
//  }
//
//  @Override
//  public List<Notification> getNotification(Integer sentTo) {
//    return notificationRepo.findNotifications(sentTo);
//  }
//
//  @Override
//  public Integer unreadNotifications(Integer sentTo) {
//    return notificationRepo.unreadNotifications(sentTo);
//  }
//}
