package com.binar.kelompokd.interfaces;

import com.binar.kelompokd.models.entity.kost.Facility;
import com.binar.kelompokd.models.request.FacilityRequest;

import java.util.List;
import java.util.UUID;

public interface FacilityService {
    Facility addFacility(FacilityRequest facilityRequest);
    Facility getFacilityById(UUID id);
    List<Facility> getAllFacilities();
    Facility editFacility(UUID id, FacilityRequest facilityRequest);
    void deleteFacility(UUID id);

    Facility addFacilityToRoom(UUID roomId, Facility facility);

    String deleteFacilityFromRoom(UUID facilityId, UUID roomId);
}
