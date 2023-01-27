package com.binar.kelompokd.interfaces;

import com.binar.kelompokd.models.entity.location.City;
import com.binar.kelompokd.models.request.CityRequest;

import java.util.List;

public interface CityService {
    City save(CityRequest cityRequest);
    City getCityById(Integer id);
    List<City> getAllCities();
    City update(Integer id, City city);
    void delete(Integer id);
}
