package com.binar.kelompokd.services;

import com.binar.kelompokd.models.entity.Image;
import com.binar.kelompokd.models.request.ImageRequest;

public interface ImageService {
    Image addImage(ImageRequest imageRequest);
    String editImage(Integer id, ImageRequest imageRequest);
    String deleteImage(Integer id);
}
