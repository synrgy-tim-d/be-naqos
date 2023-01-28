package com.binar.kelompokd.services.kost;

import com.binar.kelompokd.exception.ResourceNotFoundException;
import com.binar.kelompokd.interfaces.FacilityService;
import com.binar.kelompokd.models.entity.kost.Facility;
import com.binar.kelompokd.models.entity.kost.Room;
import com.binar.kelompokd.models.request.FacilityRequest;
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
    RoomRepository roomRepository;

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
    public Facility addFacilityToRoom(UUID roomId, Facility facilityRequest) {
        Facility facility = roomRepository.findById(roomId).map(room -> {
            UUID facilityId = facilityRequest.getId();

            // facility exist
            if(facilityId!=null){
                Optional<Facility> facility2 = facilityRepository.findById(facilityId);
                if(facility2.isPresent()){
                    room.addFacility(facility2.get());
                    roomRepository.save(room);
                    return facility2.get();
                }
            }

            // add and create new facility
            room.addFacility(facilityRequest);
            return facilityRepository.save(facilityRequest);
        }).orElseThrow(()->new ResourceNotFoundException("room with ID: " + roomId + " is not found"));

        return facility;
    }

    @Override
    public String deleteFacilityFromRoom(UUID facilityId, UUID roomId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Room with id = " + roomId));

        room.removeFacility(facilityId);
        roomRepository.save(room);
        return "success";
    }
}
