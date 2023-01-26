package com.binar.kelompokd.repos.location;

import com.binar.kelompokd.models.entity.location.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
  City findByCity(String city);
  City getCityById(Integer id);
  @Query(
          nativeQuery = true,
          value = "select * from t_setup_city where city=:name"
  )
  City getCityByName(@Param("name") String name);
}
