package com.binar.kelompokd.repos.location;

import com.binar.kelompokd.models.entity.location.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
  City findByCity(String city);
  City getCityById(Integer id);

  List<City> findAllByProvinceId(Integer provinceId);
  @Query(
          nativeQuery = true,
          value = "delete from t_setup_city where id = :id"
  )
  @Modifying
  @Transactional
  void deleteCityById(@Param("id") Integer id);
  @Query(
          nativeQuery = true,
          value = "select * from t_setup_city where city=:name"
  )
  City getCityByName(@Param("name") String name);

  @Query(
          nativeQuery = true,
          value = "select * from t_setup_city where lower(city) like lower(concat('%', concat(:name, '%')))"
  )
  List<City> getCitiesByName(@Param("name") String name);
}
