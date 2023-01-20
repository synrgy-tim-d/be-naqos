package com.binar.kelompokd.models.VO;

import com.binar.kelompokd.models.entity.Image;
import com.binar.kelompokd.models.entity.kost.Room;
import com.binar.kelompokd.models.entity.kost.RoomFacility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * class ini digunakan untuk mendapatkan object room bersamaan dengan gambar2 dan fasilitas2nya. hal ini dilakukan untuk mendapatkan detail yang bagus di response saat api di hit
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomVO {
    private Room room;
    private List<Image> images;
    private List<RoomFacility> facilities;
}
