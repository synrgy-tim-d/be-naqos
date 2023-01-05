package com.binar.kelompokd.services;

import com.binar.kelompokd.models.entity.*;
import com.binar.kelompokd.models.entity.request.KostPropertyRequest;
import com.binar.kelompokd.repos.*;
import com.binar.kelompokd.utils.SimpleStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class KostPropertyServiceImpl implements KostPropertyService {

    @Autowired
    KostPropertyRepository kostPropertyRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    KostFacilityRepository kostFacilityRepository;

    @Autowired
    KostSpecificationRepository kostSpecificationRepository;

    @Autowired
    PriceCategoryRepository priceCategoryRepository;

    @Autowired
    SubcityRepository subcityRepository;

    @Autowired
    KostTypeRepository kostTypeRepository;

    SimpleStringUtils simpleStringUtils = new SimpleStringUtils();

    @Override
    public List<KostProperty> getAllKost(int page, int size, String orderBy, String orderType) {
        Pageable pageable = simpleStringUtils.getShort(orderBy, orderType, page, size);
        return kostPropertyRepository.findAll(pageable).getContent();
    }

    @Override
    public KostProperty getKostById(int id) {
        return kostPropertyRepository.findById(id).get();
    }

    @Override
    public KostProperty save(KostPropertyRequest kostPropertyRequest) {
        Subcity subcity = subcityRepository.findById(kostPropertyRequest.getLocationId()).get();
        KostSpecification kostSpecification = kostSpecificationRepository.findById(kostPropertyRequest.getKostSpecificationId()).get();
        PricesCategory pricesCategory = priceCategoryRepository.findById(kostPropertyRequest.getPriceCategoryId()).get();
        KostType kostType = kostTypeRepository.findKostTypeById(kostPropertyRequest.getKostTypeId());
        KostFacility kostFacility = kostFacilityRepository.findById(kostPropertyRequest.getKostFacilityId()).get();
        Set<KostFacility> kostFacilities = new HashSet<>();
        kostFacilities.add(kostFacility);
        KostProperty kostProperty = KostProperty.builder()
                .location(subcity)
                .kostSpecification(kostSpecification)
                .priceCategory(pricesCategory)
                .description(kostPropertyRequest.getDescription())
                .name(kostPropertyRequest.getName())
                .street(kostPropertyRequest.getStreet())
                .photos(kostPropertyRequest.getPhotos())
                .isAvailable(kostPropertyRequest.getIsAvailable())
                .pricePerCategory(kostPropertyRequest.getPricePerCategory())
                .kostFacilities(kostFacilities)
                .kostType(kostType)
                .build();

        return kostPropertyRepository.save(kostProperty);
    }

    @Override
    public void updateKost(int id, KostProperty kostProperty) {
        KostProperty kostProperty1 = kostPropertyRepository.findById(id).get();
        kostProperty1.setName(kostProperty.getName());
        kostProperty1.setDescription(kostProperty.getDescription());
        kostProperty1.setStreet(kostProperty.getStreet());
        kostProperty1.setIsAvailable(kostProperty.getIsAvailable());
        kostPropertyRepository.save(kostProperty1);
    }

    @Override
    public void deleteKost(int id) {
        kostPropertyRepository.deleteById(id);
    }
}
