package com.binar.kelompokd.services;

import com.binar.kelompokd.models.entity.Kost;
import com.binar.kelompokd.repos.KostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class KostServiceImpl implements KostService{

    @Autowired
    KostRepository kostRepository;

    @Override
    public Kost createKost(Kost kost) {
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
    public String deleteKost(UUID id) {
        kostRepository.deleteById(kostRepository.findById(id).get().getId());
        return "Kost deleted successfully";
    }
}
