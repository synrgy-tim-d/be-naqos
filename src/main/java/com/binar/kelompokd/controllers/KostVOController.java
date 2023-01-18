package com.binar.kelompokd.controllers;

import com.binar.kelompokd.interfaces.*;
import com.binar.kelompokd.models.VO.AddressVO;
import com.binar.kelompokd.models.VO.KostVO;
import com.binar.kelompokd.models.VO.RoomVO;
import com.binar.kelompokd.models.entity.*;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/kos-vo")
public class KostVOController {

    @Autowired
    KostService kostService;

    @Autowired
    AddressService addressService;

    @Autowired
    KostRoomService kostRoomService;

    @Autowired
    CityService cityService;

    @Autowired
    ProvinceService provinceService;

    @Autowired
    RoomFacilityService roomFacilityService;

    @Autowired
    ImageService imageService;

    /**
     * method ini akan mengambil kos bersamaan dengan list kamar, list fasilitas kamar, list foto kamar, address, kota dan provincenya berdasarkan kos id.
     */
    @GetMapping("/data/{kostId}")
    public ResponseEntity<?> getKosWithAll(@PathVariable("kostId") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID kostId){

        Kost kost = kostService.getKostById(kostId).get();

        Address address = addressService.getAddressById(kost.getLocationId());

        City city = cityService.getCityById(address.getCityId());

        Province province = provinceService.getProvinceById(address.getProvinceId());

        AddressVO addressVO = AddressVO.builder().address(address).city(city).province(province).build();

        List<RoomVO> roomVOS = new ArrayList<>();

        Image image = new Image();

        RoomFacility facility = new RoomFacility();

        // 1 kos punya banyak rooms, tiap room punya banyak facilitas dan foto

        UUID[] roomIds = kost.getRoomId();

        for(int i=0;i<roomIds.length;i++){

            RoomVO roomVO = new RoomVO();
            KostRoom room = new KostRoom();
            List<Image> images = new ArrayList<>();
            List<RoomFacility> facilities = new ArrayList<>();

            room = kostRoomService.getRoomById(roomIds[i]);

            Integer[] imageIds=room.getImageId();

            for(int j=0;j<imageIds.length;j++){
                images.add(imageService.getImageById(imageIds[j]));
            }

            UUID[] facilityIds=room.getFacilityId();

            for(int j=0;j<facilityIds.length;j++){
                facilities.add(roomFacilityService.getFacilityById(facilityIds[j]));
            }

            roomVO.setRoom(room);
            roomVO.setImages(images);
            roomVO.setFacilities(facilities);

            roomVOS.add(roomVO);
        }

        KostVO kostVO = KostVO.builder().kost(kost).address(addressVO).rooms(roomVOS).build();

        return new ResponseEntity<>(kostVO, HttpStatus.OK);
    }
}
