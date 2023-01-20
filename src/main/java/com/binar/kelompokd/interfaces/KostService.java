package com.binar.kelompokd.interfaces;

import com.binar.kelompokd.models.entity.kost.Kost;
import com.binar.kelompokd.models.request.KostRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface KostService {
    Kost createKost(Kost kost);
    Optional<Kost> getKostById(UUID id);
    List<Kost> getAllKost();
    Page<Kost> getAllKost(Pageable pageable);
    Kost updateKost(UUID id, Kost kost);
    String deleteKost(UUID id);
//    Kost addArrays(UUID kostId, UUID roomId, KostRoomFacilityImageRequest request);
    Page<Kost> getListData(Pageable pageable);
    String softDeleteKost(UUID id);
}
