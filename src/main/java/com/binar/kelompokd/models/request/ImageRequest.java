package com.binar.kelompokd.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageRequest {
    @Column(columnDefinition="TEXT")
    @NotNull
    private String fileLocation;
}
