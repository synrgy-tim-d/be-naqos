package com.binar.kelompokd.interfaces;

import com.binar.kelompokd.models.entity.location.City;
import com.binar.kelompokd.models.request.location.CityRequest;

import java.util.List;

public interface CityService {
    City save(CityRequest cityRequest);
    City getCityById(Integer id);
    List<City> getAllCities();
    List<City> getCityByProvince(Integer id);
    City update(Integer id, CityRequest cityRequest);
    void delete(Integer id);
}
