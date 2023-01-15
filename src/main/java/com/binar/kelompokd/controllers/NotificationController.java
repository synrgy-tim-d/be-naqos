package com.binar.kelompokd.controllers;

import com.binar.kelompokd.interfaces.INotificationService;
import com.binar.kelompokd.interfaces.IUserAuthService;
import com.binar.kelompokd.models.dto.notification.NotificationUnreadCountDTO;
import com.binar.kelompokd.models.dto.notification.ReadNotificationDTO;
import com.binar.kelompokd.models.entity.Notification;
import com.binar.kelompokd.models.entity.oauth.Users;
import com.binar.kelompokd.models.response.MessageResponse;
import com.binar.kelompokd.models.response.NotificationPageResponse;
import com.binar.kelompokd.models.response.NotificationResponse;
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
@RequestMapping("/notification")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NotificationController {
  @Autowired
  private INotificationService iNotificationService;

  @Autowired
  private IUserAuthService iUserService;

  @PutMapping("/read")
  public ResponseEntity<MessageResponse> readNotification(@RequestBody ReadNotificationDTO request) {
    iNotificationService.updateIsRead(request.getId());
    return ResponseEntity.ok(new MessageResponse("Notification Read."));
  }

  @PutMapping("/all-read")
  public ResponseEntity<MessageResponse> markAllAsRead(Authentication authentication) {
    Users user = iUserService.findByEmail(authentication.getName());
    iNotificationService.markAllAsRead(user.getId());
    return ResponseEntity.ok(new MessageResponse("All Notifications Read."));
  }

  @GetMapping("/unread")
  public ResponseEntity<NotificationUnreadCountDTO> countNotifications(Authentication authentication) {
    Users user = iUserService.findByEmail(authentication.getName());
    Integer unread = iNotificationService.unreadNotifications(user.getId());

    NotificationUnreadCountDTO response = new NotificationUnreadCountDTO(user, unread);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/get")
  public ResponseEntity<NotificationPageResponse> getNotificationAuth(Authentication authentication,
                                                                      @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                                                      @RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {

    Users user = iUserService.findByEmail(authentication.getName());
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
