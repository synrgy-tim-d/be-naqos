package com.binar.kelompokd.services;

import com.binar.kelompokd.models.entity.*;
import com.binar.kelompokd.models.request.KostRequest;
import com.binar.kelompokd.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class KostServiceImpl implements KostService{

    @Autowired
    KostRepository kostRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    ProvinceRepository provinceRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    KostRoomRepository kostRoomRepository;

    @Autowired
    RoomFacilityRepository roomFacilityRepository;

    @Override
    @Transactional
    public Kost createKost(KostRequest kostRequest) {

        City city = City.builder()
                .city(kostRequest.getCity())
                .build();

        cityRepository.save(city);

        Province province = Province.builder()
                .province(kostRequest.getProvince())
                .build();

        provinceRepository.save(province);

        Address address = Address.builder()
                .city(city)
                .address(kostRequest.getAddress())
                .district(kostRequest.getDistrict())
                .subdistrict(kostRequest.getSubdistrict())
                .postalCode(kostRequest.getPostalCode())
                .longitude(kostRequest.getLongitude())
                .latitude(kostRequest.getLatitude())
                .province(province)
                .build();

        addressRepository.save(address);

        Kost kost = Kost.builder()
                .name(kostRequest.getName())
                .kostType(kostRequest.getKostType())
                .roomId(kostRequest.getRoomId())
                .description(kostRequest.getDescription())
                .location(address)
                .isAvailable(false)
                .build();

        return kostRepository.save(kost);
    }

    @Override
    public Optional<Kost> getKostById(UUID id) {
        return kostRepository.findById(id);
    }

    @Override
    public List<Kost> getAllKost() {
        return kostRepository.findAll();
    }

    @Override
    @Transactional
    public String updateKost(UUID id, Kost kost) {

        Kost kostUpdated = kostRepository.findById(id).get();

        kostUpdated.setName(kost.getName());
        kostUpdated.setDescription(kost.getDescription());
        kostUpdated.setKostType(kost.getKostType());
        kostUpdated.setLocation(kost.getLocation());
        kostUpdated.setRoomId(kost.getRoomId());
        kostUpdated.setUpdatedAt(new Date());
        kostUpdated.setIsAvailable(kost.getIsAvailable());

        kostRepository.save(kostUpdated);

        return "Kost updated successfully";
    }

    @Override
    @Transactional
    public String deleteKost(UUID id) {
        kostRepository.deleteById(kostRepository.findById(id).get().getId());
        return "Kost deleted successfully";
    }
}
