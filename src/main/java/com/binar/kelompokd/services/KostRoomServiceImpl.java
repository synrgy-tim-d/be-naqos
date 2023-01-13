package com.binar.kelompokd.services;

import com.binar.kelompokd.models.entity.KostRoom;
import com.binar.kelompokd.models.request.KostRoomRequest;
import com.binar.kelompokd.repos.KostRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class KostRoomServiceImpl implements KostRoomService{

    @Autowired
    KostRoomRepository kostRoomRepository;

    @Transactional
    @Override
    public KostRoom addRoom(KostRoomRequest kostRoomRequest) {
        KostRoom kostRoom = KostRoom.builder()
                .roomType(kostRoomRequest.getRoom_type())
                .rules(kostRoomRequest.getRules())
                .pricePerMonthly(kostRoomRequest.getPrice_per_monthly())
                .pricePerWeekly(kostRoomRequest.getPrice_per_weekly())
                .pricePerDaily(kostRoomRequest.getPrice_per_daily())
                .isAvailable(kostRoomRequest.getIs_available())
                .imageId(kostRoomRequest.getImage_id())
                .facilityId(kostRoomRequest.getFacility_id())
                .build();
        return kostRoomRepository.save(kostRoom);
    }

    @Override
    @Transactional
    public KostRoom updateRoom(Integer id, KostRoomRequest kostRoomRequest) {

        KostRoom kostRoom = kostRoomRepository.findById(id).get();

        kostRoom.setRoomType(kostRoomRequest.getRoom_type());
        kostRoom.setRules(kostRoomRequest.getRules());
        kostRoom.setPricePerDaily(kostRoomRequest.getPrice_per_daily());
        kostRoom.setPricePerWeekly(kostRoomRequest.getPrice_per_weekly());
        kostRoom.setPricePerMonthly(kostRoomRequest.getPrice_per_monthly());
        kostRoom.setImageId(kostRoomRequest.getImage_id());
        kostRoom.setFacilityId(kostRoomRequest.getFacility_id());
        kostRoom.setIsAvailable(kostRoomRequest.getIs_available());

        return kostRoomRepository.save(kostRoom);
    }

    @Override
    @Transactional
    public String deleteRoom(Integer id) {
        kostRoomRepository.deleteById(id);
        return "Kost room deleted successfully";
    }

    @Override
    public List<KostRoom> getAllRooms() {
        return kostRoomRepository.findAll();
    }

    @Override
    public KostRoom getRoomById(Integer id) {
        return kostRoomRepository.findById(id).get();
    }
}
