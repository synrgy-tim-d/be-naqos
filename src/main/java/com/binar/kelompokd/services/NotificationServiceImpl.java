package com.binar.kelompokd.services;

import com.binar.kelompokd.interfaces.INotificationService;
import com.binar.kelompokd.interfaces.IUserAuthService;
import com.binar.kelompokd.models.entity.Notification;
import com.binar.kelompokd.models.entity.oauth.Users;
import com.binar.kelompokd.repos.NotificationRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements INotificationService {

  @Autowired
  private NotificationRepo notificationRepo;

  @Autowired
  private IUserAuthService iUserService;

  @Override
  public void saveNotification(String title, String content, Long userId) {
    Notification notification = new Notification();
    Users user = iUserService.findUsersById(userId);
    notification.setTitle(title);
    notification.setContent(content);
    notification.setSentTo(user);
    notificationRepo.save(notification);
  }

  @Override
  public void updateIsRead(Integer id) {
    Optional<Notification> notification = notificationRepo.findById(id);
    notification.ifPresent(notification1 -> {
      notification1.setIsRead(true);
      notificationRepo.save(notification1);
    });
  }

  @Override
  public void markAllAsRead(Long sentTo) {
    List<Notification> notificationList = notificationRepo.getAllNotificationsWhereIsReadFalse(sentTo);

    for (Notification notification : notificationList) {
      notification.setIsRead(true);
      notificationRepo.save(notification);
    }
  }

  @Override
  public List<Notification> getNotification(Long sentTo) {
    return notificationRepo.findNotifications(sentTo);
  }

  @Override
  public Integer unreadNotifications(Long sentTo) {
    return notificationRepo.unreadNotifications(sentTo);
  }
}
