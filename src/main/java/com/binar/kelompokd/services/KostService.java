package com.binar.kelompokd.services;

import com.binar.kelompokd.models.entity.Kost;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface KostService {
    Kost createKost(Kost kost);
    Optional<Kost> getKostById(UUID id);
    List<Kost> getAllKost();
    String updateKost(UUID id, Kost kost);
    String deleteKost(UUID id);
}
