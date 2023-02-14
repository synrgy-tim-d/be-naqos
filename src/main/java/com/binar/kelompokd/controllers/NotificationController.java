package com.binar.kelompokd.controllers;

import com.binar.kelompokd.interfaces.INotificationService;
import com.binar.kelompokd.interfaces.IUserAuthService;
import com.binar.kelompokd.models.entity.Notification;
import com.binar.kelompokd.models.entity.oauth.Users;
import com.binar.kelompokd.models.response.MessageResponse;
import com.binar.kelompokd.models.response.notification.NotificationPageResponse;
import com.binar.kelompokd.models.response.notification.NotificationResponse;
import com.binar.kelompokd.models.response.notification.NotificationUnreadCountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/notifications")
@Tag(name = "Notification Management", description = "APIs for Managing Notification")
public class NotificationController {
  @Autowired
  private INotificationService iNotificationService;
  @Autowired
  private IUserAuthService iUserService;

  @Operation(summary = "Update unread notification when notification has been read by user", tags = {"Notification Management"})
  @PutMapping("/read")
  public ResponseEntity<MessageResponse> readNotification(@RequestParam Integer id) {
    iNotificationService.updateIsRead(id);
    return ResponseEntity.ok(new MessageResponse("Notification Read."));
  }

  @Operation(summary = "Update all unread notification when notification has been read by user", tags = {"Notification Management"})
  @PutMapping("/all-read")
  public ResponseEntity<MessageResponse> markAllAsRead(Authentication authentication) {
    Users user = iUserService.findByUsername(authentication.getName());
    iNotificationService.markAllAsRead(user.getId());
    return ResponseEntity.ok(new MessageResponse("All Notifications Read."));
  }

  @Operation(summary = "Count all unread notification", tags = {"Notification Management"})
  @GetMapping("/unread")
  public ResponseEntity<NotificationUnreadCountResponse> countNotifications(Authentication authentication) {
    Users user = iUserService.findByUsername(authentication.getName());
    Integer unread = iNotificationService.unreadNotifications(user.getId());

    NotificationUnreadCountResponse response = new NotificationUnreadCountResponse(user, unread);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @Operation(summary = "List notification", tags = {"Notification Management"})
  @GetMapping("/get")
  public ResponseEntity<?> getNotificationAuth(Authentication authentication,
                                               @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                               @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {

    Users user = iUserService.findByUsername(authentication.getName());
    List<Notification> notification = iNotificationService.getNotification(user.getId());

    List<NotificationResponse> notificationResponses =
        notification.stream()
            .map(notification1 -> {
              return new NotificationResponse(notification1);
            })
            .collect(Collectors.toList());

    // buat create pageable
    int notifSize = notificationResponses.size();
    Pageable paging = PageRequest.of(page, size);
    int start = Math.min((int) paging.getOffset(), notifSize);
    int end = Math.min((start + paging.getPageSize()), notifSize);
    Page<NotificationResponse> paged = new PageImpl<>(notificationResponses.subList(start, end), paging, notifSize);

    NotificationPageResponse pagedNotificationResponses = new NotificationPageResponse(paged, page);
    return new ResponseEntity<>(pagedNotificationResponses, HttpStatus.OK);
  }
}
