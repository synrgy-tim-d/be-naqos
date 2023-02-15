package com.binar.kelompokd.interfaces;

import com.binar.kelompokd.models.entity.kost.Room;
import com.binar.kelompokd.models.request.kost.RoomRequest;

import java.util.List;
import java.util.UUID;

public interface RoomService {
    Room addRoom(RoomRequest roomRequest);
    Room updateRoom(UUID id, RoomRequest roomRequest);
    String deleteRoom(UUID id);
    List<Room> getAllRooms();
    List<Room> getAllAvailableRoomsByKostId(UUID kostId);
    Room getRoomById(UUID id);
    String softDeleteRoom(UUID id);
    void newDeleteRoom(UUID id);
}
