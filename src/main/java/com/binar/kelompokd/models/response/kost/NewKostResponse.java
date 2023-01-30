package com.binar.kelompokd.models.response.kost;

import com.binar.kelompokd.enums.KostType;
import com.binar.kelompokd.models.entity.Image;
import com.binar.kelompokd.models.entity.kost.Kost;
import com.binar.kelompokd.models.entity.oauth.Users;
import com.binar.kelompokd.models.response.user.UserResponse;
import com.binar.kelompokd.models.response.location.CityResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class NewKostResponse {
  private UUID id;
  private String name;
  private String description;
  private KostType kostType;
  private Boolean isAvailable;
  private Double latitude;
  private Double longitude;
  private String address;
  private String subdistrict;
  private String district;
  private String postalCode;
  private CityResponse city;
  private UserResponse user;
  private List<String> imgUrl;

  public NewKostResponse(Kost kosts, Users user) {
    this.user = new UserResponse(user);
    this.id = kosts.getId();
    this.name = kosts.getName();
    this.description = kosts.getDescription();
    this.kostType = kosts.getKostType();
    this.isAvailable = kosts.getIsAvailable();
    this.latitude = kosts.getLatitude();
    this.longitude = kosts.getLongitude();
    this.address = kosts.getAddress();
    this.subdistrict = kosts.getSubdistrict();
    this.district = kosts.getDistrict();
    this.postalCode = kosts.getPostalCode();
    this.city = new CityResponse(kosts.getCity());
    this.imgUrl = kosts.getImageKosts().stream().map(Image::getUrl).collect(Collectors.toList());
  }

}
