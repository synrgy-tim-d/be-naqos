package com.binar.kelompokd.interfaces;

import com.binar.kelompokd.models.entity.City;

import java.util.List;

public interface CityService {
    City save(City city);
    City getCityById(Integer id);
    List<City> getAllCities();
    City update(Integer id, City city);
    void delete(Integer id);
}
