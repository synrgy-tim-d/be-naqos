package com.binar.kelompokd.models.request;

import com.binar.kelompokd.enums.Condition;
import com.binar.kelompokd.enums.RoomType;
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
public class KostRoomRequest {
    @NotNull
    @Enumerated(EnumType.STRING)
    private RoomType room_type;

    // rules text
    @Column(columnDefinition="TEXT")
    @NotNull
    private String rules;

    private Double price_per_daily;
    private Double price_per_weekly;

    @Column(nullable = false)
    private Double price_per_monthly;

    @NotNull
    private Boolean is_available;

    // facility_id array[]
    private Integer[] facility_id;

    // image_id array[]
    private Integer[] image_id;
}
