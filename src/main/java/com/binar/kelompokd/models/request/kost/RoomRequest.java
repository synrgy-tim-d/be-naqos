package com.binar.kelompokd.models.request.kost;

import com.binar.kelompokd.enums.RoomType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomRequest {
    @NotNull
    @Enumerated(EnumType.STRING)
    @Schema(example = "FULL_FURNISHED")
    private RoomType room_type;

    // rules text
    @Column(columnDefinition="TEXT")
    @NotNull
    @Schema(example = "Jangan berisik di atas jam 10 malam")
    private String rules;

    @Schema(example = "150000")
    private BigDecimal price_per_daily;

    @Schema(example = "650000")
    private BigDecimal price_per_weekly;

    @NotNull
    @Schema(example = "2000000")
    private BigDecimal price_per_monthly;

    @NotNull
    @Schema(example = "true")
    private Boolean is_available;

    @NotNull
    @Schema(example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID kostId;
}
