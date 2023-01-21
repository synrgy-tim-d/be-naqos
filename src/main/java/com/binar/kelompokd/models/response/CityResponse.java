package com.binar.kelompokd.models.response;

import com.binar.kelompokd.models.entity.location.City;
import com.binar.kelompokd.models.entity.oauth.Users;
import lombok.Data;

@Data
public class CityResponse {
  private Integer id;
  private String city;

  public CityResponse(City city) {
    this.id = city.getId();
    this.city = city.getCity();
  }
}
