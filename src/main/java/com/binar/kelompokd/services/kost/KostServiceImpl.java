package com.binar.kelompokd.services.kost;

import com.binar.kelompokd.interfaces.KostService;
import com.binar.kelompokd.models.entity.Image;
import com.binar.kelompokd.models.entity.kost.Kost;
import com.binar.kelompokd.repos.ImageRepository;
import com.binar.kelompokd.repos.kost.KostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class KostServiceImpl implements KostService {

    @Autowired
    KostRepository kostRepository;

    @Autowired
    ImageRepository imageRepository;

    @Override
    @Transactional
    public Kost createKost(Kost kost) {
        return kostRepository.save(kost);
    }

    @Override
    public Optional<Kost> getKostById(UUID id) {
        return kostRepository.getKostByIdWhereIsAvailableTrue(id);
    }

    @Override
    public List<Kost> getAllKost() {
        return kostRepository.getAllKostWhereIsAvailableTrue();
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
        return kostRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public String softDeleteKost(UUID id) {
        kostRepository.softDeleteKost(id);
        return "Kost deleted successfully";
    }

}
