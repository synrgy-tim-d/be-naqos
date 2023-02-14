package com.binar.kelompokd.service.kost;

import com.binar.kelompokd.models.entity.kost.Kost;
import com.binar.kelompokd.repos.kost.KostRepository;
import com.binar.kelompokd.services.kost.KostServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KostServiceImplTest {
  @Mock
  private KostRepository repository;

  @InjectMocks
  private KostServiceImpl service;

  @Test
  @DisplayName("Assert find Kost by id and return Kost")
  void getKostByIdTest() {
    UUID kostId = UUID.randomUUID();
    Kost kost = new Kost();
    kost.setId(kostId);
    kost.setName("name");
    kost.setAddress("Jl Aku padamu");
    kost.setPostalCode("17115");
    kost.setDistrict("Pengumban");
    kost.setLatitude(-6.123123D);
    kost.setLongitude(106.123123D);
    kost.setSubdistrict("Rawalumbu");
    kost.setDescription("Kost Description Testing");
    kost.setIsAvailable(true);
    when(repository.findById(kostId)).thenReturn(Optional.of(kost));
    assertSame(kost, service.getKostById(kostId));
  }

  @Test
  @DisplayName("Assert Kost by Name and return Kost")
  void getKostByKostNameTest() {
    UUID kostID = UUID.randomUUID();
    String kostName = "Kost Testing";
    Kost kosts = new Kost();
    kosts.setId(kostID);
    kosts.setName(kostName);
    when(repository.getKostByName(kostName)).thenReturn(kosts);
    assertSame(kosts, service.getKostByName(kostName));
  }
}
