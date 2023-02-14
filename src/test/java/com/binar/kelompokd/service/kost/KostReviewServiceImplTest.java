package com.binar.kelompokd.service.kost;

import com.binar.kelompokd.models.entity.kost.KostReview;
import com.binar.kelompokd.repos.kost.KostReviewRepository;
import com.binar.kelompokd.services.kost.KostReviewServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KostReviewServiceImplTest {
  @Mock
  private KostReviewRepository repository;

  @InjectMocks
  private KostReviewServiceImpl service;

  @Test
  @DisplayName("Assert get KostReview return list KostReview for user")
  void getKostReviewByKostIdTest() {
    UUID kostID = UUID.randomUUID();
    List<KostReview> kostReview = new ArrayList<>();
    when(repository.findReviewByKostId(kostID)).thenReturn(kostReview);
    assertSame(kostReview, service.getReviewByKostId(kostID));
  }
}
