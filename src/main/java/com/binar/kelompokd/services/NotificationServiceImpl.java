package com.binar.kelompokd.services;

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
