package com.binar.kelompokd.models.response;

import com.binar.kelompokd.models.entity.Kost;
import com.binar.kelompokd.models.entity.KostRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KostResponse {
    private List<Kost> kos;
}
