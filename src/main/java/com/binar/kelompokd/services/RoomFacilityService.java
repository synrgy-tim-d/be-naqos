package com.binar.kelompokd.services;

import com.binar.kelompokd.models.entity.RoomFacility;
import com.binar.kelompokd.models.request.RoomFacilityRequest;

public interface RoomFacilityService {
    RoomFacility addFacility(RoomFacilityRequest roomFacilityRequest);
    String editFacility(Integer id, RoomFacilityRequest roomFacilityRequest);
    String deleteFacility(Integer id);
}
