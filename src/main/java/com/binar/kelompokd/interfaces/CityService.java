package com.binar.kelompokd.interfaces;

import com.binar.kelompokd.models.entity.location.City;

import java.util.List;

public interface CityService {
    City save(City city);
    City getCityById(Integer id);
    List<City> getAllCities();
    City update(Integer id, City city);
    void delete(Integer id);
}
