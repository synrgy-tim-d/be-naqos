package com.binar.kelompokd.services.kost;

import com.binar.kelompokd.models.entity.kost.Kost;
import com.binar.kelompokd.repos.kost.KostRepository;
import com.binar.kelompokd.utils.exception.ResourceNotFoundException;
import com.binar.kelompokd.interfaces.FacilityService;
import com.binar.kelompokd.models.entity.kost.Facility;
import com.binar.kelompokd.models.entity.kost.Room;
import com.binar.kelompokd.models.request.kost.FacilityRequest;
import com.binar.kelompokd.repos.kost.FacilityRepository;
import com.binar.kelompokd.repos.kost.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FacilityServiceImpl implements FacilityService {

    @Autowired
    FacilityRepository facilityRepository;

    @Autowired
    KostRepository kostRepository;

    @Override
    @Transactional
    public Facility addFacility(FacilityRequest facilityRequest) {

        Facility facility = Facility.builder()
                .name(facilityRequest.getName())
                .condition(facilityRequest.getCondition())
                .isActive(facilityRequest.getIsActive())
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
    public Facility editFacility(UUID id, FacilityRequest facilityRequest) {

        Facility facility = facilityRepository.findById(id).get();

        facility.setName(facilityRequest.getName());
        facility.setCondition(facilityRequest.getCondition());
        facility.setIsActive(facilityRequest.getIsActive());

        return facilityRepository.save(facility);
    }

    @Override
    @Transactional
    public void deleteFacility(UUID id) {
        facilityRepository.deleteById(id);
    }

    @Override
    public Object addFacilityToKost(UUID kostId, Facility facilityRequest) {
        Facility facility = kostRepository.findById(kostId).map(kost -> {
            UUID facilityId = facilityRequest.getId();

            // facility exist
            if(facilityId!=null){
                Optional<Facility> facility2 = facilityRepository.findById(facilityId);
                if(facility2.isPresent()){
                    kost.addFacility(facility2.get());
                    kostRepository.save(kost);
                    return facility2.get();
                }
            }

            // add and create new facility
            kost.addFacility(facilityRequest);
            return facilityRepository.save(facilityRequest);
        }).orElseThrow(()->new ResourceNotFoundException("kost with ID: " + kostId + " is not found"));

        return facility;
    }

    @Override
    public Object deleteFacilityFromKost(UUID kostId, UUID facilityId) {
        Kost kost = kostRepository.findById(kostId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Kost with id = " + kostId));

        kost.removeFacility(facilityId);
        kostRepository.save(kost);
        return "success";
    }
}
