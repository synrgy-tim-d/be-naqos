package com.binar.kelompokd.services;

import com.binar.kelompokd.models.entity.Image;
import com.binar.kelompokd.models.request.ImageRequest;

public interface ImageService {
    Image addImage(ImageRequest imageRequest);
    Image editImage(Integer id, ImageRequest imageRequest);
    String deleteImage(Integer id);
}
