package com.binar.kelompokd.services.location;

import com.binar.kelompokd.interfaces.CityService;
import com.binar.kelompokd.models.entity.location.City;
import com.binar.kelompokd.models.entity.location.Province;
import com.binar.kelompokd.models.request.CityRequest;
import com.binar.kelompokd.repos.location.CityRepository;
import com.binar.kelompokd.repos.location.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    ProvinceRepository provinceRepository;

    @Override
    @Transactional
    public City save(CityRequest cityRequest) {
        Province province = provinceRepository.findById(cityRequest.getProvinceId()).get();

        City city = City.builder()
                .city(cityRequest.getCity())
                .province(province)
                .build();

        return cityRepository.save(city);
    }

    @Override
    public City getCityById(Integer id) {
        return cityRepository.findById(id).get();
    }

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    @Transactional
    public City update(Integer id, City city) {
        City city1 = cityRepository.findById(id).get();

        city1.setCity(city.getCity());

        return cityRepository.save(city1);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        cityRepository.deleteById(id);
    }
}
