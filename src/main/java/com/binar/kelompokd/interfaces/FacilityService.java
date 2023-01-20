package com.binar.kelompokd.interfaces;

import com.binar.kelompokd.models.entity.kost.Facility;
import com.binar.kelompokd.models.request.RoomFacilityRequest;

import java.util.List;
import java.util.UUID;

public interface FacilityService {
    Facility addFacility(RoomFacilityRequest roomFacilityRequest);
    Facility getFacilityById(UUID id);
    List<Facility> getAllFacilities();
    Facility editFacility(UUID id, RoomFacilityRequest roomFacilityRequest);
    void deleteFacility(UUID id);
}
