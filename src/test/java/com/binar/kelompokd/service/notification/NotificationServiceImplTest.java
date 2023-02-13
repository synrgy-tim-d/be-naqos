package com.binar.kelompokd.service.notification;

import com.binar.kelompokd.models.entity.Notification;
import com.binar.kelompokd.repos.notification.NotificationRepo;
import com.binar.kelompokd.services.NotificationServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {
  @Mock
  private NotificationRepo repository;

  @InjectMocks
  private NotificationServiceImpl service;

  @Test
  @DisplayName("Assert get Notification return list Notification for user")
  void getNotification() {
    Long userId = 1L;
    List<Notification> notifications = new ArrayList<>();
    when(repository.findNotifications(userId)).thenReturn(notifications);
    assertSame(notifications, service.getNotification(userId));
  }
}
