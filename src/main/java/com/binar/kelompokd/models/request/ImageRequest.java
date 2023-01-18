package com.binar.kelompokd.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "www.foto-kamar-1.com")
    @Column(columnDefinition="TEXT")
    @NotNull
    private String fileLocation;
}
