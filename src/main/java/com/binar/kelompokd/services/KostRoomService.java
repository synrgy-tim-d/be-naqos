package com.binar.kelompokd.services;

import com.binar.kelompokd.models.entity.KostRoom;
import com.binar.kelompokd.models.request.KostRoomRequest;

import java.util.List;

public interface KostRoomService {
    KostRoom addRoom(KostRoomRequest kostRoomRequest);
    String updateRoom(Integer id, KostRoomRequest kostRoomRequest);
    String deleteRoom(Integer id);

    List<KostRoom> getAllRooms();
}
