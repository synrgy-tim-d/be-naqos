package com.binar.kelompokd.service.kost;

import com.binar.kelompokd.models.entity.Image;
import com.binar.kelompokd.repos.ImageRepository;
import com.binar.kelompokd.services.ImageServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {
  @Mock
  private ImageRepository repository;

  @InjectMocks
  private ImageServiceImpl service;

  @Test
  @DisplayName("Assert get Image return Image for user")
  void findImageKostByUrlTest(){
    String url = "testing";
    Image images = new Image();
    images.setUrl(url);
    images.setCreatedAt(new Date());
    when(repository.findImageKostByUrl(url)).thenReturn(images);
    assertSame(images, service.findImageKostByUrl(url));
  }
}
