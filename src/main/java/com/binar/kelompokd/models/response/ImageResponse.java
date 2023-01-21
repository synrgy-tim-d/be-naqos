package com.binar.kelompokd.models.response;

import com.binar.kelompokd.models.entity.Image;
import lombok.Data;

import java.util.List;

@Data
public class ImageResponse {
  private List<Image> image;
}
