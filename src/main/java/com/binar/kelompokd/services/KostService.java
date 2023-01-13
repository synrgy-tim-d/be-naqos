package com.binar.kelompokd.services;

import com.binar.kelompokd.models.entity.Kost;
import com.binar.kelompokd.models.request.KostRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface KostService {
    Kost createKost(KostRequest kostRequest);
    Optional<Kost> getKostById(UUID id);
    List<Kost> getAllKost();
    String updateKost(UUID id, KostRequest kostRequest);
    String deleteKost(UUID id);
}
