package com.binar.kelompokd.interfaces;

import com.binar.kelompokd.models.entity.kost.Kost;
import com.binar.kelompokd.models.response.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface KostService {
    Kost getKostById(UUID id);
    List<Kost> getAllKost();
    Page<Kost> getAllKost(Pageable pageable);
    void deleteKostById(UUID id);
//    Kost addArrays(UUID kostId, UUID roomId, KostRoomFacilityImageRequest request);
    Page<Kost> getListData(Pageable pageable);
    String softDeleteKost(UUID id);

    Kost getKostByName(String kost);
    void saveKost(UUID uuid, String name, String description,String kostType,Boolean isAvailable,Double latitude,Double longitude,
                  String address, String subdistrict, String district, String postalCode, Long ownerId, Integer city);

    ResponseEntity<MessageResponse> getMessageResponse(Integer page, Integer size, Page<Kost> kosts);
    Page<Kost> getKostsByKostType(String kostType, Pageable pageable);
    Page<Kost> getKostsByCityId(Integer cityId, Pageable pageable);
    Page<Kost> getKostsByCity(String cityName, Pageable pageable);

//    void updateKost(UUID uuid, String name, String description, String kostType,Boolean isAvailable,Double latitude,Double longitude,
//                    String address, String subdistrict, String district, String postalCode, Integer city);

    Kost updateKost(UUID id, Kost kost);
}
