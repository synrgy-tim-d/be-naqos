package com.binar.kelompokd.models.VO;

import com.binar.kelompokd.models.entity.kost.Kost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * class ini digunakan untuk mendapatkan object kos bersamaan dengan addressnya dan room2nya. hal ini dilakukan untuk mendapatkan detail yang bagus di response saat api di hit
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KostVO {
    private Kost kost;
    private AddressVO address;
    private List<RoomVO> rooms;
}
