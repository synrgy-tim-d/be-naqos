package com.binar.kelompokd.models.VO;

import com.binar.kelompokd.models.entity.Address;
import com.binar.kelompokd.models.entity.City;
import com.binar.kelompokd.models.entity.Province;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * class ini digunakan untuk mendapatkan object address dan province dan city. hal ini dilakukan untuk mendapatkan detail yang bagus di response saat api di hit
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressVO {
    private Address address;
    private Province province;
    private City city;
}
