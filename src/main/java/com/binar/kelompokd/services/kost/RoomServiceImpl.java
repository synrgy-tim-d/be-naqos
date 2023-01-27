package com.binar.kelompokd.services.kost;

import com.binar.kelompokd.interfaces.RoomService;
import com.binar.kelompokd.models.entity.kost.Facility;
import com.binar.kelompokd.models.entity.kost.Kost;
import com.binar.kelompokd.models.entity.kost.Room;
import com.binar.kelompokd.models.request.RoomRequest;
import com.binar.kelompokd.repos.kost.FacilityRepository;
import com.binar.kelompokd.repos.kost.KostRepository;
import com.binar.kelompokd.repos.kost.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    KostRepository kostRepository;

    @Autowired
    FacilityRepository facilityRepository;

    @Transactional
    @Override
    public Room addRoom(RoomRequest roomRequest) {

        BigDecimal pricePerDaily = new BigDecimal(0);
        BigDecimal pricePerWeekly = new BigDecimal(0);

        if(roomRequest.getPrice_per_daily()!=null){
            pricePerDaily = roomRequest.getPrice_per_daily();
        }

        if(roomRequest.getPrice_per_weekly()!=null){
            pricePerWeekly = roomRequest.getPrice_per_weekly();
        }

        Kost kost = kostRepository.findById(roomRequest.getKostId()).get();

        Set<Facility> facilities = new HashSet<>();

        for(UUID facilityIds: roomRequest.getFacility_id()){
            Facility facility = facilityRepository.findById(facilityIds).get();
            facilities.add(facility);
        }

        Room room = Room.builder()
                .roomType(roomRequest.getRoom_type())
                .rules(roomRequest.getRules())
                .pricePerDaily(pricePerDaily)
                .pricePerWeekly(pricePerWeekly)
                .pricePerMonthly(roomRequest.getPrice_per_monthly())
                .isAvailable(roomRequest.getIs_available())
                .kost(kost)
                .facility(facilities)
                .build();

        return roomRepository.save(room);
    }

    @Override
    @Transactional
    public Room updateRoom(UUID id, Room room) {

        Room roomUpdated = roomRepository.findById(id).get();

        roomUpdated.setRoomType(room.getRoomType());
        roomUpdated.setRules(room.getRules());
        roomUpdated.setPricePerDaily(room.getPricePerDaily());
        roomUpdated.setPricePerWeekly(room.getPricePerWeekly());
        roomUpdated.setPricePerMonthly(room.getPricePerMonthly());
        roomUpdated.setIsAvailable(room.getIsAvailable());
        roomUpdated.setKost(room.getKost());
        roomUpdated.setFacility(room.getFacility());
        roomUpdated.setUpdatedAt(new Date());

        return roomRepository.save(roomUpdated);
    }

    @Override
    @Transactional
    public String deleteRoom(UUID id) {
        roomRepository.deleteById(id);
        return "Kost room deleted successfully";
    }

    @Override
    @Transactional
    public String softDeleteRoom(UUID id) {
        roomRepository.softDeleteRoom(id);
        return "Kost room deleted successfully";
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.getAllRoomsWhereIsAvailableTrue();
    }

    @Override
    public Room getRoomById(UUID id) {
        return roomRepository.getRoomByIdWhereIsAvailableTrue(id).get();
    }
}
