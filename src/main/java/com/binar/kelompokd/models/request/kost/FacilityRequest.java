package com.binar.kelompokd.models.request.kost;

import com.binar.kelompokd.enums.Condition;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FacilityRequest {
    @NotNull
    @Column(length = 50)
    @Schema(example = "AC")
    private String name;

    @Enumerated(EnumType.STRING)
    @Schema(example = "BARU")
    private Condition condition;

    @NotNull
    @Schema(example = "true")
    private Boolean isActive;
}
