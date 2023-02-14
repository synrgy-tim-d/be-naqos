package com.binar.kelompokd.service.kost;

import com.binar.kelompokd.models.entity.kost.KostWishlist;
import com.binar.kelompokd.repos.kost.KostWishlistRepository;
import com.binar.kelompokd.services.kost.KostWishlistServiceImpl;
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
class KostWishlistServiceImplTest {
  @Mock
  private KostWishlistRepository repository;

  @InjectMocks
  private KostWishlistServiceImpl service;

  @Test
  @DisplayName("Assert get KostWishlist return list KostWishlist for user")
  void getAWishlistTest(){
    Long userId = 1L;
    UUID kostID = UUID.randomUUID();
    List<KostWishlist> kostWishlists = new ArrayList<>();
    when(repository.getAWishlistByKostIdAndUserId(kostID, userId)).thenReturn(kostWishlists);
    assertSame(kostWishlists, service.getAWishlist(kostID, userId));
  }
}
