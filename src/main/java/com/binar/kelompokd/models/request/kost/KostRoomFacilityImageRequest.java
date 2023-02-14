package com.binar.kelompokd.models.request.kost;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "[\"123e4567-e89b-12d3-a456-426614174000\"]")
    private UUID[] roomId;

    @NotNull
    @Schema(example = "[\"123e4567-e89b-12d3-a456-426614174000\"]")
    private UUID[] facilityId;

    @NotNull
    @Schema(example = "[\"1\"]")
    private Integer[] imageId;
}
