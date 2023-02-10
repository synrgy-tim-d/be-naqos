package com.binar.kelompokd.controller;

import com.binar.kelompokd.controllers.NotificationController;
import com.binar.kelompokd.interfaces.INotificationService;
import com.binar.kelompokd.interfaces.IUserAuthService;
import com.binar.kelompokd.models.entity.Notification;
import com.binar.kelompokd.models.entity.oauth.Users;
import com.binar.kelompokd.models.response.notification.NotificationPageResponse;
import com.binar.kelompokd.models.response.notification.NotificationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NotificationControllerTest {
  @InjectMocks
  private NotificationController notificationController;
  @Mock
  private INotificationService iNotificationService;
  @Mock
  private IUserAuthService iUserService;
  private MockMvc mockMvc;
  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();
  }

  @Test
  void getNotificationAuthTest() throws Exception{
    Users user = new Users();
    user.setId(1L);
    user.setUsername("test");
    List<Notification> notifications = new ArrayList<>();
    Notification n1 = new Notification();
    n1.setId(1);
    n1.setTitle("test");
    n1.setContent("testing 123");
    n1.setSentTo(user);
    notifications.add(n1);

    Page<NotificationResponse> paged = new PageImpl<>(new ArrayList<>(), PageRequest.of(0, 10), notifications.size());
    NotificationPageResponse pagedNotificationResponses = new NotificationPageResponse(paged, 0);

    when(iUserService.findByUsername(any(String.class))).thenReturn(user);
    when(iNotificationService.getNotification(user.getId())).thenReturn(notifications);

    Authentication auth = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
    SecurityContextHolder.getContext().setAuthentication(auth);

    mockMvc.perform(get("/get").param("page", "0").param("size", "10")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }
}
