package com.binar.kelompokd.interfaces;

import com.binar.kelompokd.models.entity.Image;
import com.binar.kelompokd.models.request.ImageRequest;

import java.util.List;

public interface ImageService {
    Image addImage(ImageRequest imageRequest);
    Image getImageById(Integer id);
    List<Image> getAllImages();
    Image editImage(Integer id, ImageRequest imageRequest);
    String deleteImage(Integer id);
}
