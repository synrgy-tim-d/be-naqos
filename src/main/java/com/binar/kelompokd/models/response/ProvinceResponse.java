package com.binar.kelompokd.models.response;

import com.binar.kelompokd.models.entity.location.Province;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class ProvinceResponse {
  private List<Province> provinces;
  public ProvinceResponse(List<Province> prov) {
    this.provinces = prov;
  }
}
