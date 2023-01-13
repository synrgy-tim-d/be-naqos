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
    private RoomType roomType;

    // rules text
    @Column(columnDefinition="TEXT")
    @NotNull
    private String rules;

    private Double pricePerDaily;
    private Double pricePerWeekly;

    @Column(nullable = false)
    private Double pricePerMonthly;

}
