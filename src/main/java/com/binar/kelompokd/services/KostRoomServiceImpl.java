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
                .roomType(kostRoomRequest.getRoomType())
                .rules(kostRoomRequest.getRules())
                .pricePerMonthly(kostRoomRequest.getPricePerMonthly())
                .pricePerWeekly(kostRoomRequest.getPricePerWeekly())
                .pricePerDaily(kostRoomRequest.getPricePerDaily())
                .isAvailable(false)
                .build();
        return kostRoomRepository.save(kostRoom);
    }

    @Override
    @Transactional
    public String updateRoom(Integer id, KostRoomRequest kostRoomRequest) {

        KostRoom kostRoom = kostRoomRepository.findById(id).get();

        kostRoom.setRoomType(kostRoomRequest.getRoomType());
        kostRoom.setRules(kostRoomRequest.getRules());
        kostRoom.setPricePerDaily(kostRoomRequest.getPricePerDaily());
        kostRoom.setPricePerWeekly(kostRoomRequest.getPricePerWeekly());
        kostRoom.setPricePerMonthly(kostRoomRequest.getPricePerMonthly());

        kostRoomRepository.save(kostRoom);

        return "Kost room updated successfully";
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
}
