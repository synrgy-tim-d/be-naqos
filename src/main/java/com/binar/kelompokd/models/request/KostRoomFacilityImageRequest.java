package com.binar.kelompokd.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

// this class is for assigning Kost class arrays;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KostRoomFacilityImageRequest {
    @NotNull
    private UUID[] roomId;

    @NotNull
    private UUID[] facilityId;

    @NotNull
    private Integer[] imageId;
}
