package com.binar.kelompokd.models.request;

import com.binar.kelompokd.enums.Condition;
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
public class KostRoomRequest {
    @NotNull
    @Enumerated(EnumType.STRING)
    @Schema(example = "FULL_FURNISHED")
    private RoomType room_type;

    // rules text
    @Column(columnDefinition="TEXT")
    @NotNull
    @Schema(example = "Jangan berisik di atas jam 10 malam")
    private String rules;

    @NotNull
    @Schema(example = "150000")
    private BigDecimal price_per_daily;

    @NotNull
    @Schema(example = "650000")
    private BigDecimal price_per_weekly;

    @NotNull
    @Schema(example = "2000000")
    private BigDecimal price_per_monthly;

    @NotNull
    @Schema(example = "true")
    private Boolean is_available;

    // facility_id array[]
    @NotNull
    @Schema(example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID[] facility_id; // untuk membuat room kita perlu membuat facility dulu agar mendapatkan uuid nya

    // image_id array[]
    @NotNull
    @Schema(example = "1")
    private Integer[] image_id; // untuk membuat room kita perlu membuat image dulu agar mendapatkan uuid nya
}
