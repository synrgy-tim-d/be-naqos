package com.binar.kelompokd.services;

import com.binar.kelompokd.models.entity.RoomFacility;
import com.binar.kelompokd.models.request.RoomFacilityRequest;

import java.util.UUID;

public interface RoomFacilityService {
    RoomFacility addFacility(RoomFacilityRequest roomFacilityRequest);
    RoomFacility editFacility(UUID id, RoomFacilityRequest roomFacilityRequest);
    void deleteFacility(UUID id);
}
