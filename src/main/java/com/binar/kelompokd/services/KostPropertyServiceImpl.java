package com.binar.kelompokd.services;

import com.binar.kelompokd.models.entity.KostProperty;
import com.binar.kelompokd.repos.KostPropertyRepository;
import com.binar.kelompokd.utils.SimpleStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KostPropertyServiceImpl implements KostPropertyService {

    @Autowired
    KostPropertyRepository kostPropertyRepository;

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
    public KostProperty save(KostProperty kostProperty) {
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
