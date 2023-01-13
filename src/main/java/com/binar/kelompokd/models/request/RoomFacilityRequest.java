package com.binar.kelompokd.models.request;

import com.binar.kelompokd.enums.Condition;
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
public class RoomFacilityRequest {
    @NotNull
    @Column(length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    private Condition condition;
}
