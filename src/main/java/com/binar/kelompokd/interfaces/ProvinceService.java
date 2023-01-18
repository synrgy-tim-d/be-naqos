package com.binar.kelompokd.interfaces;

import com.binar.kelompokd.models.entity.Province;

import java.util.List;

public interface ProvinceService {
    Province save(Province province);
    Province getProvinceById(Integer id);
    List<Province> getAllProvinces();
    Province update(Integer id, Province province);
    void delete(Integer id);
}
