//package com.binar.kelompokd.services.kost;
//
//import com.binar.kelompokd.interfaces.KostService;
//import com.binar.kelompokd.models.entity.kost.Kost;
//import com.binar.kelompokd.models.request.KostRequest;
//import com.binar.kelompokd.repos.*;
//import com.binar.kelompokd.repos.kost.KostRepository;
//import com.binar.kelompokd.repos.kost.RoomRepository;
//import com.binar.kelompokd.repos.kost.RoomFacilityRepository;
//import com.binar.kelompokd.repos.location.AddressRepository;
//import com.binar.kelompokd.repos.location.CityRepository;
//import com.binar.kelompokd.repos.location.ProvinceRepository;
//import com.binar.kelompokd.utils.SimpleStringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//public class KostServiceImpl implements KostService {
//
//    @Autowired
//    KostRepository kostRepository;
//
//    @Autowired
//    AddressRepository addressRepository;
//
//    @Autowired
//    CityRepository cityRepository;
//
//    @Autowired
//    ProvinceRepository provinceRepository;
//
//    @Autowired
//    ImageRepository imageRepository;
//
//    @Autowired
//    RoomRepository roomRepository;
//
//    @Autowired
//    RoomFacilityRepository roomFacilityRepository;
//
//    @Autowired
//    SimpleStringUtils simpleStringUtils;
//
//    @Override
//    @Transactional
//    public Kost createKost(KostRequest kostRequest) {
//
////        City city = City.builder()
////                .city(kostRequest.getCity())
////                .build();
////
////        cityRepository.save(city);
////
////        Province province = Province.builder()
////                .province(kostRequest.getProvince())
////                .build();
////
////        provinceRepository.save(province);
//
////        Address address = Address.builder()
////                .city(city)
////                .address(kostRequest.getAddress())
////                .district(kostRequest.getDistrict())
////                .subdistrict(kostRequest.getSubdistrict())
////                .postalCode(kostRequest.getPostalCode())
////                .longitude(kostRequest.getLongitude())
////                .latitude(kostRequest.getLatitude())
////                .province(province)
////                .build();
////
////        addressRepository.save(address);
//
//        Kost kost = Kost.builder()
//                .name(kostRequest.getName())
//                .kostType(kostRequest.getKost_type())
//                .description(kostRequest.getDescription())
//                .locationId(kostRequest.getLocation_id())
//                .roomId(kostRequest.getRoom_id())
////                .location(address)
//                .isAvailable(kostRequest.getIs_available())
//                .build();
//
//        return kostRepository.save(kost);
//    }
//
//    @Override
//    public Optional<Kost> getKostById(UUID id) {
//        return kostRepository.getKostByIdWhereIsAvailableTrue(id);
//    }
//
//    @Override
//    public List<Kost> getAllKost() {
//        return kostRepository.getAllKostWhereIsAvailableTrue();
//    }
//
//    @Override
//    @Transactional
//    public Kost updateKost(UUID id, KostRequest kostRequest) {
//
//        Kost kostUpdated = kostRepository.findById(id).get();
//
//        kostUpdated.setUpdatedAt(new Date());
//        kostUpdated.setKostType(kostRequest.getKost_type());
//        kostUpdated.setName(kostRequest.getName());
//        kostUpdated.setDescription(kostRequest.getDescription());
//        kostUpdated.setIsAvailable(kostRequest.getIs_available());
//        kostUpdated.setRoomId(kostRequest.getRoom_id());
//        kostUpdated.setLocationId(kostRequest.getLocation_id());
//
////        Integer addressId = kostUpdated.getLocation().getId();
////        Address location = addressRepository.findById(addressId).get();
////
////        location.setAddress(kostRequest.getAddress());
////
////        City cityUpdated = location.getCity();
////       cityUpdated.setCity(kostRequest.getCity());
////        location.setCity(cityUpdated);
////        location.setDistrict(kostRequest.getDistrict());
////        location.setSubdistrict(kostRequest.getSubdistrict());
////        location.setLongitude(kostRequest.getLongitude());
////        location.setLatitude(kostRequest.getLatitude());
////
////        Province province = location.getProvince();
////        province.setProvince(kostRequest.getProvince());
////        location.setProvince(province);
////        location.setPostalCode(kostRequest.getPostalCode());
////        kostUpdated.setLocation(location);
//
//        return kostRepository.save(kostUpdated);
//    }
//
//    @Override
//    @Transactional
//    public String deleteKost(UUID id) {
//        kostRepository.deleteById(id);
//        return "Kost deleted successfully";
//    }
//
////    @Override
////    @Transactional
////    public Kost addArrays(UUID kostId, UUID roomId, KostRoomFacilityImageRequest request) {
////        Kost kost = kostRepository.findById(kostId).get();
////
////        kost.setRoomId(request.getRoomId());
////
////        kostRepository.save(kost);
////
////        Room kostRoom = roomRepository.findById(roomId).get();
////
////        kostRoom.setFacilityId(request.getFacilityId());
////        kostRoom.setImageId(request.getImageId());
////
////        roomRepository.save(kostRoom);
////
////        return kost;
////    }
//
//    @Override
//    public Page<Kost> getListData(Pageable pageable) {
//        return kostRepository.findAll(pageable);
//    }
//
//    @Override
//    @Transactional
//    public String softDeleteKost(UUID id) {
//        kostRepository.softDeleteKost(id);
//        return "Kost deleted successfully";
//    }
//
//}
