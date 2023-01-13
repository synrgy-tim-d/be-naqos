package com.binar.kelompokd.services;

import com.binar.kelompokd.models.entity.RoomFacility;
import com.binar.kelompokd.models.request.RoomFacilityRequest;

public interface RoomFacilityService {
    RoomFacility addFacility(RoomFacilityRequest roomFacilityRequest);
    RoomFacility editFacility(Integer id, RoomFacilityRequest roomFacilityRequest);
    void deleteFacility(Integer id);
}
