package com.binar.kelompokd.services.kost;

import com.binar.kelompokd.enums.KostType;
import com.binar.kelompokd.interfaces.CityService;
import com.binar.kelompokd.interfaces.IUserAuthService;
import com.binar.kelompokd.interfaces.KostService;
import com.binar.kelompokd.models.entity.kost.Kost;
import com.binar.kelompokd.models.entity.location.City;
import com.binar.kelompokd.models.entity.oauth.Users;
import com.binar.kelompokd.models.response.MessageResponse;
import com.binar.kelompokd.models.response.NewKostResponse;
import com.binar.kelompokd.models.response.WishlistKostPageResponse;
import com.binar.kelompokd.repos.ImageRepository;
import com.binar.kelompokd.repos.kost.KostRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
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

    private IUserAuthService userAuthService;
    private CityService cityService;

    @Override
    @Transactional
    public Kost createKost(Kost kost) {
        return kostRepository.save(kost);
    }

    @Override
    public Kost getKostById(UUID id) {
        return kostRepository.findKostById(id);
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
    public Kost updateKost(UUID id, Kost kost) {

        Kost kostUpdated = kostRepository.findById(id).get();

        kostUpdated.setUpdatedAt(new Date());
        kostUpdated.setName(kost.getName());
        kostUpdated.setDescription(kost.getDescription());
        kostUpdated.setKostType(kost.getKostType());
        kostUpdated.setIsAvailable(kost.getIsAvailable());
        kostUpdated.setLatitude(kost.getLatitude());
        kostUpdated.setLongitude(kost.getLongitude());
        kostUpdated.setAddress(kost.getAddress());
        kostUpdated.setDistrict(kost.getDistrict());
        kostUpdated.setSubdistrict(kost.getSubdistrict());
        kostUpdated.setPostalCode(kost.getPostalCode());
        kostUpdated.setCity(kost.getCity());

        return kostRepository.save(kostUpdated);
    }

    @Override
    @Transactional
    public String deleteKost(UUID id) {
        kostRepository.deleteById(id);
        return "Kost deleted successfully";
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
            WishlistKostPageResponse wishlistResponsePage = new WishlistKostPageResponse(kosts.getTotalPages(),
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
}
