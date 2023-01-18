package com.binar.kelompokd.interfaces;

import com.binar.kelompokd.models.entity.Kost;
import com.binar.kelompokd.models.request.KostRequest;
import com.binar.kelompokd.models.request.KostRoomFacilityImageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface KostService {
    Kost createKost(KostRequest kostRequest);
    Optional<Kost> getKostById(UUID id);
    List<Kost> getAllKost();
    Kost updateKost(UUID id, KostRequest kostRequest);
    String deleteKost(UUID id);
//    Kost addArrays(UUID kostId, UUID roomId, KostRoomFacilityImageRequest request);
    Page<Kost> getListData(Pageable pageable);
}
