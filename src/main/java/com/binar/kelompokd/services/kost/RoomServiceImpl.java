//package com.binar.kelompokd.services.kost;
//
//import com.binar.kelompokd.interfaces.RoomService;
//import com.binar.kelompokd.models.entity.kost.Room;
//import com.binar.kelompokd.models.request.KostRoomRequest;
//import com.binar.kelompokd.repos.kost.RoomRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class RoomServiceImpl implements RoomService {
//
//    @Autowired
//    RoomRepository roomRepository;
//
//    @Transactional
//    @Override
//    public Room addRoom(KostRoomRequest kostRoomRequest) {
//        Room room = Room.builder()
//                .roomType(kostRoomRequest.getRoom_type())
//                .rules(kostRoomRequest.getRules())
//                .pricePerMonthly(kostRoomRequest.getPrice_per_monthly())
//                .pricePerWeekly(kostRoomRequest.getPrice_per_weekly())
//                .pricePerDaily(kostRoomRequest.getPrice_per_daily())
//                .isAvailable(kostRoomRequest.getIs_available())
//                .imageId(kostRoomRequest.getImage_id())
//                .facilityId(kostRoomRequest.getFacility_id())
//                .build();
//
//        room.setCreatedAt(new Date());
//
//        return roomRepository.save(room);
//    }
//
//    @Override
//    @Transactional
//    public Room updateRoom(UUID id, KostRoomRequest kostRoomRequest) {
//
//        Room room = roomRepository.findById(id).get();
//
//        room.setRoomType(kostRoomRequest.getRoom_type());
//        room.setRules(kostRoomRequest.getRules());
//        room.setPricePerDaily(kostRoomRequest.getPrice_per_daily());
//        room.setPricePerWeekly(kostRoomRequest.getPrice_per_weekly());
//        room.setPricePerMonthly(kostRoomRequest.getPrice_per_monthly());
//        room.setImageId(kostRoomRequest.getImage_id());
//        room.setFacilityId(kostRoomRequest.getFacility_id());
//        room.setIsAvailable(kostRoomRequest.getIs_available());
//        room.setUpdatedAt(new Date());
//
//        return roomRepository.save(room);
//    }
//
//    @Override
//    @Transactional
//    public String deleteRoom(UUID id) {
//        roomRepository.deleteById(id);
//        return "Kost room deleted successfully";
//    }
//
//    @Override
//    @Transactional
//    public String softDeleteRoom(UUID id) {
//        roomRepository.softDeleteRoom(id);
//        return "Kost room deleted successfully";
//    }
//
//    @Override
//    public List<Room> getAllRooms() {
//        return roomRepository.getAllRoomsWhereIsAvailableTrue();
//    }
//
//    @Override
//    public Room getRoomById(UUID id) {
//        return roomRepository.getRoomByIdWhereIsAvailableTrue(id).get();
//    }
//}
