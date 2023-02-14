package com.binar.kelompokd.services.kost;

import com.binar.kelompokd.enums.KostType;
import com.binar.kelompokd.interfaces.CityService;
import com.binar.kelompokd.interfaces.IUserAuthService;
import com.binar.kelompokd.interfaces.KostService;
import com.binar.kelompokd.models.QueryParams;
import com.binar.kelompokd.models.entity.kost.Kost;
import com.binar.kelompokd.models.entity.location.City;
import com.binar.kelompokd.models.entity.oauth.Users;
import com.binar.kelompokd.models.response.MessageResponse;
import com.binar.kelompokd.models.response.kost.NewKostResponse;
import com.binar.kelompokd.models.specifications.KostsSpecificationBuilder;
import com.binar.kelompokd.repos.ImageRepository;
import com.binar.kelompokd.repos.kost.KostRepository;
import com.binar.kelompokd.repos.location.CityRepository;
import com.binar.kelompokd.utils.exception.ResourceNotFoundException;
import com.binar.kelompokd.utils.response.PageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class KostServiceImpl implements KostService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

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
    public void saveKost(UUID uuid, String name, String description, String kostType, Boolean isAvailable, Double latitude, Double longitude, String address,
                         String fQuestion1, String fAnswer1, String fQuestion2, String fAnswer2, String fQuestion3, String fAnswer3,
                         BigDecimal pricePerDaily, BigDecimal pricePerWeekly, BigDecimal pricePerMonthly, String rules,
                         String subdistrict, String district, String postalCode, Long ownerId, Integer city) {
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
        kost.setQuestion1(fQuestion1);
        kost.setAnswer1(fAnswer1);
        kost.setQuestion2(fQuestion2);
        kost.setAnswer2(fAnswer2);
        kost.setQuestion3(fQuestion3);
        kost.setAnswer3(fAnswer3);
        kost.setPricePerDaily(pricePerDaily);
        kost.setPricePerWeekly(pricePerWeekly);
        kost.setPricePerMonthly(pricePerMonthly);
        kost.setRules(rules);
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
    public Page<Kost> getKostsByCity2(String cityName, Pageable pageable) {
        List<City> cities = cityRepository.getCitiesByName(cityName);
        List<Kost> kosts = new ArrayList<>();
        for(City c:cities){
            Integer cityId = c.getId();
            kosts.addAll(kostRepository.getKostsByCityId(cityId, pageable).getContent());
        }
        return new PageImpl<>(kosts);
    }

    @Override
    public void updateKost(UUID uuid, String name, String description, String kostType, Boolean isAvailable, Double latitude, Double longitude, String address,
                           String fQuestion1, String fAnswer1, String fQuestion2, String fAnswer2, String fQuestion3, String fAnswer3,
                           BigDecimal pricePerDaily, BigDecimal pricePerWeekly, BigDecimal pricePerMonthly, String rules,
                           String subdistrict, String district, String postalCode, Integer city) {
        Kost existing = kostRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("Kost not found with id " + uuid));
        existing.setName(name);
        existing.setDescription(description);
        existing.setKostType(KostType.valueOf(kostType));
        existing.setIsAvailable(isAvailable);
        existing.setLatitude(latitude);
        existing.setLongitude(longitude);
        existing.setAddress(address);
        existing.setDistrict(district);
        existing.setSubdistrict(subdistrict);
        existing.setPostalCode(postalCode);
        existing.setQuestion1(fQuestion1);
        existing.setAnswer1(fAnswer1);
        existing.setQuestion2(fQuestion2);
        existing.setAnswer2(fAnswer2);
        existing.setQuestion3(fQuestion3);
        existing.setAnswer3(fAnswer3);
        existing.setPricePerDaily(pricePerDaily);
        existing.setPricePerWeekly(pricePerWeekly);
        existing.setPricePerMonthly(pricePerMonthly);
        existing.setRules(rules);
        City cityKost = cityService.getCityById(city);
        existing.setCity(cityKost);
        kostRepository.save(existing);
//        kostRepository.updateKost(uuid, name, description, kostType, isAvailable, latitude, longitude, address, subdistrict, district, postalCode, city);
    }

    @Override
    public PageResponse getKost (QueryParams params) throws Exception {
        Page<Kost> paged = kostRepository.findAll(
                new KostsSpecificationBuilder().build(params),
                params.getPagination());

        return new PageResponse(
                paged.getTotalPages(),
                paged.getTotalElements(),
                paged.getPageable().getPageNumber() + 1,
                paged.isFirst(),
                paged.isLast(),
                paged.getSize(),
                OBJECT_MAPPER.convertValue(paged.getContent(), List.class));
    }

}
