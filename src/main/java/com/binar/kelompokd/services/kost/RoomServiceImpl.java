package com.binar.kelompokd.services.kost;

import com.binar.kelompokd.interfaces.RoomService;
import com.binar.kelompokd.models.entity.kost.Room;
import com.binar.kelompokd.models.request.KostRoomRequest;
import com.binar.kelompokd.repos.kost.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    RoomRepository roomRepository;

    @Transactional
    @Override
    public Room addRoom(Room room) {
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
