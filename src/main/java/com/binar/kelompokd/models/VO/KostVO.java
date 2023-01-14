package com.binar.kelompokd.models.VO;

import com.binar.kelompokd.models.entity.Address;
import com.binar.kelompokd.models.entity.Kost;
import com.binar.kelompokd.models.entity.KostRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KostVO {
    private Kost kost;
    private AddressVO address;
    private List<RoomVO> rooms;
}
