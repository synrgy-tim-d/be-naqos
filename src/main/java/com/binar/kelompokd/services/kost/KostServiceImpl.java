package com.binar.kelompokd.services.kost;

import com.binar.kelompokd.enums.KostType;
import com.binar.kelompokd.interfaces.CityService;
import com.binar.kelompokd.interfaces.IUserAuthService;
import com.binar.kelompokd.interfaces.KostService;
import com.binar.kelompokd.models.entity.kost.Kost;
import com.binar.kelompokd.models.entity.location.City;
import com.binar.kelompokd.models.entity.oauth.Users;
import com.binar.kelompokd.models.response.MessageResponse;
import com.binar.kelompokd.models.response.kost.NewKostResponse;
import com.binar.kelompokd.repos.ImageRepository;
import com.binar.kelompokd.repos.kost.KostRepository;
import com.binar.kelompokd.repos.location.CityRepository;
import com.binar.kelompokd.utils.response.PageResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class KostServiceImpl implements KostService {

    @Autowired
    KostRepository kostRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    CityRepository cityRepository;

    private IUserAuthService userAuthService;
    private CityService cityService;

    @Override
    public Kost getKostById(UUID id) {
        return kostRepository.findById(id).get();
    }

    @Override
    public List<Kost> getAllKost() {
        return kostRepository.getAllKostWhereIsAvailableTrue();
    }

    @Override
    public Page<Kost> getAllKost(Pageable pageable) {
        return kostRepository.getAllKostWhereIsAvailableTrue(pageable);
    }

    @Override
    @Transactional
    public void deleteKostById(UUID id) {
        kostRepository.deleteKostById(id);
    }

    @Override
    public Page<Kost> getListData(Pageable pageable) {
        return kostRepository.getAllKostWhereIsAvailableTrue(pageable);
    }

    @Override
    @Transactional
    public String softDeleteKost(UUID id) {
        kostRepository.softDeleteKost(id);
        return "Kost deleted successfully";
    }

    @Override
    public Kost getKostByName(String kost) {
        return kostRepository.getKostByName(kost);
    }

    @Override
    public void saveKost(UUID uuid,String name, String description, String kostType, Boolean isAvailable, Double latitude, Double longitude, String address, String subdistrict, String district, String postalCode, Long ownerId, Integer city) {
        Kost kost = new Kost();
        kost.setId(uuid);
        kost.setName(name);
        kost.setDescription(description);
        kost.setKostType(KostType.valueOf(kostType));
        kost.setIsAvailable(isAvailable);
        kost.setLatitude(latitude);
        kost.setLongitude(longitude);
        kost.setAddress(address);
        kost.setDistrict(district);
        kost.setSubdistrict(subdistrict);
        kost.setPostalCode(postalCode);
        City cityKost = cityService.getCityById(city);
        kost.setCity(cityKost);
        Users user = userAuthService.findUsersById(ownerId);
        kost.setOwnerId(user);
        kostRepository.save(kost);
    }

    @Override
    public ResponseEntity<MessageResponse> getMessageResponse(Integer page, Integer size, Page<Kost> kosts) {
        List<NewKostResponse> productResponses = kosts.stream()
            .map(product -> new NewKostResponse(product, product.getOwnerId()))
            .collect(Collectors.toList());
        if (kosts.hasContent()) {
            PageResponse wishlistResponsePage = new PageResponse(kosts.getTotalPages(),
                kosts.getTotalElements(), page, kosts.isFirst(), kosts.isLast(),
                size, productResponses);
            return new ResponseEntity(wishlistResponsePage, HttpStatus.OK);
        } else {
            return new ResponseEntity(new MessageResponse("Data Empty"), HttpStatus.NO_CONTENT);
        }

    }

    @Override
    public Page<Kost> getKostsByKostType(String kostType, Pageable pageable) {
        return kostRepository.getKostsByKostType(kostType, pageable);
    }

    @Override
    public Page<Kost> getKostsByCityId(Integer cityId, Pageable pageable) {
        return kostRepository.getKostsByCityId(cityId, pageable);
    }

    @Override
    public Page<Kost> getKostsByCity(String cityName, Pageable pageable) {
        City city = cityRepository.getCityByName(cityName);
        Integer cityId = city.getId();
        return kostRepository.getKostsByCityId(cityId, pageable);
    }

    @Override
    public void updateKost(UUID uuid, String name, String description, String kostType, Boolean isAvailable, Double latitude, Double longitude, String address, String subdistrict, String district, String postalCode, Integer city) {
        kostRepository.updateKost(uuid, name, description, kostType, isAvailable, latitude, longitude, address, subdistrict, district, postalCode, city);
    }


}
