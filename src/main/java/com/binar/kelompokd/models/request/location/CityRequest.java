package com.binar.kelompokd.models.request.location;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityRequest {
    @NotNull
    @Schema(example = "Kota Depok")
    private String city;

    @NotNull
    @Schema(example = "4")
    private Integer provinceId;
}
