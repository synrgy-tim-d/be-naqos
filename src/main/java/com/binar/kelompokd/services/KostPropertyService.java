package com.binar.kelompokd.services;

import com.binar.kelompokd.models.entity.KostProperty;

import java.util.List;

public interface KostPropertyService {
    List<KostProperty> getAllKost(int page, int size, String orderBy, String orderType);

    KostProperty getKostById(int id);

    KostProperty save(KostProperty kostProperty);

    void updateKost(int id, KostProperty kostProperty);

    void deleteKost(int id);
}
