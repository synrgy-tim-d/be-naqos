package com.binar.kelompokd.interfaces;

import com.binar.kelompokd.models.QueryParams;
import com.binar.kelompokd.models.entity.kost.Kost;
import com.binar.kelompokd.models.response.MessageResponse;
import com.binar.kelompokd.utils.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface KostService {
    Kost getKostById(UUID id);
    PageResponse getKost(QueryParams params) throws Exception;
    void deleteKostById(UUID id);
    String softDeleteKost(UUID id);
    Kost getKostByName(String kost);
    void saveKost(UUID uuid, String name, String description, String kostType, Boolean isAvailable, Double latitude, Double longitude, String address,
                  String fQuestion1, String fAnswer1, String fQuestion2, String fAnswer2, String fQuestion3, String fAnswer3,
                  BigDecimal pricePerDaily, BigDecimal pricePerWeekly, BigDecimal pricePerMonthly, String rules,
                  String subdistrict, String district, String postalCode, Long ownerId, Integer city);
    ResponseEntity<MessageResponse> getMessageResponse(Integer page, Integer size, Page<Kost> kosts);
    void updateKost(UUID uuid, String name, String description, String kostType, Boolean isAvailable, Double latitude, Double longitude, String address,
                    String fQuestion1, String fAnswer1, String fQuestion2, String fAnswer2, String fQuestion3, String fAnswer3,
                    BigDecimal pricePerDaily, BigDecimal pricePerWeekly, BigDecimal pricePerMonthly, String rules,
                    String subdistrict, String district, String postalCode, Integer city);
}
