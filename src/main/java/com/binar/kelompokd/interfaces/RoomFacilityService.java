package com.binar.kelompokd.interfaces;

import com.binar.kelompokd.models.entity.RoomFacility;
import com.binar.kelompokd.models.request.RoomFacilityRequest;

import java.util.List;
import java.util.UUID;

public interface RoomFacilityService {
    RoomFacility addFacility(RoomFacilityRequest roomFacilityRequest);
    RoomFacility getFacilityById(UUID id);
    List<RoomFacility> getAllFacilities();
    RoomFacility editFacility(UUID id, RoomFacilityRequest roomFacilityRequest);
    void deleteFacility(UUID id);
}
