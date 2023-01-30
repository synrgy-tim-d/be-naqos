package com.binar.kelompokd.services.kost;

import com.binar.kelompokd.interfaces.RoomService;
import com.binar.kelompokd.models.entity.kost.Facility;
import com.binar.kelompokd.models.entity.kost.Kost;
import com.binar.kelompokd.models.entity.kost.Room;
import com.binar.kelompokd.models.request.kost.RoomRequest;
import com.binar.kelompokd.repos.kost.KostRepository;
import com.binar.kelompokd.repos.kost.RoomRepository;
import com.binar.kelompokd.repos.kost.RoomAndFacilityRepository;
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
    RoomAndFacilityRepository roomAndFacilityRepository;

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

        Room room = Room.builder()
                .roomType(roomRequest.getRoom_type())
                .rules(roomRequest.getRules())
                .pricePerDaily(pricePerDaily)
                .pricePerWeekly(pricePerWeekly)
                .pricePerMonthly(roomRequest.getPrice_per_monthly())
                .isAvailable(roomRequest.getIs_available())
                .kost(kost)
                .build();

        return roomRepository.save(room);
    }

    @Override
    @Transactional
    public Room updateRoom(UUID id, RoomRequest roomRequest) {

        BigDecimal pricePerDaily = new BigDecimal(0);
        BigDecimal pricePerWeekly = new BigDecimal(0);

        if(roomRequest.getPrice_per_daily()!=null){
            pricePerDaily = roomRequest.getPrice_per_daily();
        }

        if(roomRequest.getPrice_per_weekly()!=null){
            pricePerWeekly = roomRequest.getPrice_per_weekly();
        }

        Kost kost = kostRepository.findById(roomRequest.getKostId()).get();

        Room room = roomRepository.findById(id).get();

        Set<Facility> facilities = new HashSet<>();

        room.setRoomType(roomRequest.getRoom_type());
        room.setRules(roomRequest.getRules());
        room.setPricePerDaily(pricePerDaily);
        room.setPricePerWeekly(pricePerWeekly);
        room.setPricePerMonthly(roomRequest.getPrice_per_monthly());
        room.setIsAvailable(roomRequest.getIs_available());
        room.setKost(kost);
        room.setFacilities(facilities);
        room.setUpdatedAt(new Date());

        return roomRepository.save(room);
    }

    @Override
    @Transactional
    public String deleteRoom(UUID id) {
        Room room = roomRepository.findById(id).get();
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
    @Transactional
    public void newDeleteRoom(UUID id) {
        roomAndFacilityRepository.deleteByRoomId(id);
        roomRepository.deleteById(id);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.getAllRoomsWhereIsAvailableTrue();
    }

    @Override
    public List<Room> getAllAvailableRoomsByKostId(UUID kostId) {
        return roomRepository.getAllAvailableRoomsByKostId(kostId);
    }

    @Override
    public Room getRoomById(UUID id) {
        return roomRepository.getRoomByIdWhereIsAvailableTrue(id).get();
    }
}
