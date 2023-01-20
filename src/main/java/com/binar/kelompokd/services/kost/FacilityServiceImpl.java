package com.binar.kelompokd.services.kost;

import com.binar.kelompokd.interfaces.FacilityService;
import com.binar.kelompokd.models.entity.kost.Facility;
import com.binar.kelompokd.models.request.RoomFacilityRequest;
import com.binar.kelompokd.repos.kost.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class FacilityServiceImpl implements FacilityService {

    @Autowired
    FacilityRepository facilityRepository;

    @Override
    @Transactional
    public Facility addFacility(RoomFacilityRequest roomFacilityRequest) {

        Facility facility = Facility.builder()
                .name(roomFacilityRequest.getName())
                .condition(roomFacilityRequest.getCondition())
                .isActive(roomFacilityRequest.getIsActive())
                .build();

        return facilityRepository.save(facility);
    }

    @Override
    public Facility getFacilityById(UUID id) {
        return facilityRepository.findById(id).get();
    }

    @Override
    public List<Facility> getAllFacilities() {
        return facilityRepository.findAll();
    }

    @Override
    @Transactional
    public Facility editFacility(UUID id, RoomFacilityRequest roomFacilityRequest) {

        Facility facility = facilityRepository.findById(id).get();

        facility.setName(roomFacilityRequest.getName());
        facility.setCondition(roomFacilityRequest.getCondition());
        facility.setIsActive(roomFacilityRequest.getIsActive());

        return facilityRepository.save(facility);
    }

    @Override
    @Transactional
    public void deleteFacility(UUID id) {
        facilityRepository.deleteById(id);
    }
}
