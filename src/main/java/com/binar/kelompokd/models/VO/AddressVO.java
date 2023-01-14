package com.binar.kelompokd.models.VO;

import com.binar.kelompokd.models.entity.Address;
import com.binar.kelompokd.models.entity.City;
import com.binar.kelompokd.models.entity.Province;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressVO {
    private Address address;
    private Province province;
    private City city;
}
