package com.binar.kelompokd.models.VO;

import com.binar.kelompokd.models.entity.Image;
import com.binar.kelompokd.models.entity.KostRoom;
import com.binar.kelompokd.models.entity.RoomFacility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomVO {
    private KostRoom room;
    private List<Image> images;
    private List<RoomFacility> facilities;
}
