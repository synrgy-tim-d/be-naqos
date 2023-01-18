package com.binar.kelompokd.services;

import com.binar.kelompokd.interfaces.ProvinceService;
import com.binar.kelompokd.models.entity.Province;
import com.binar.kelompokd.repos.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    ProvinceRepository provinceRepository;

    @Override
    @Transactional
    public Province save(Province province) {
        return provinceRepository.save(province);
    }

    @Override
    public Province getProvinceById(Integer id) {
        return provinceRepository.findById(id).get();
    }

    @Override
    public List<Province> getAllProvinces() {
        return provinceRepository.findAll();
    }

    @Override
    @Transactional
    public Province update(Integer id, Province province) {
        Province province1 = provinceRepository.findById(id).get();

        province1.setProvince(province.getProvince());

        return provinceRepository.save(province1);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        provinceRepository.deleteById(id);
    }
}
