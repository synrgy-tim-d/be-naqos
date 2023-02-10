package com.binar.kelompokd.controller;

import com.binar.kelompokd.controllers.PublicController;
import com.binar.kelompokd.interfaces.IKostReviewService;
import com.binar.kelompokd.interfaces.IUserAuthService;
import com.binar.kelompokd.interfaces.KostService;
import com.binar.kelompokd.utils.SimpleStringUtils;
import com.binar.kelompokd.utils.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublicControllerTest {
  @InjectMocks
  private PublicController publicController;
  @Mock
  private KostService kostService;
  @Mock
  private IKostReviewService kostReviewService;
  @Mock
  private IUserAuthService userAuthService;
  @Mock
  private SimpleStringUtils simpleStringUtils;
  @Mock
  private Response response;

  @Test
  void getKostReviewByKostIdTest() {
    UUID id = UUID.randomUUID();
    when(kostReviewService.getReviewByKostId(id)).thenReturn(Collections.emptyList());

    ResponseEntity<?> responseEntity = publicController.getKostReviewByKostId(id);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(Collections.emptyMap(), responseEntity.getBody());
  }

  @Test
  void getKostReviewByKostIdNotFoundTest() {
    UUID id = UUID.randomUUID();
    when(kostReviewService.getReviewByKostId(id)).thenThrow(new NoSuchElementException());
    ResponseEntity<?> responseEntity = publicController.getKostReviewByKostId(id);
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
  }
}
