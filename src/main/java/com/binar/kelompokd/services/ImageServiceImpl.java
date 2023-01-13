package com.binar.kelompokd.services;

import com.binar.kelompokd.models.entity.Image;
import com.binar.kelompokd.models.request.ImageRequest;
import com.binar.kelompokd.repos.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ImageServiceImpl implements ImageService{

    @Autowired
    ImageRepository imageRepository;

    @Override
    @Transactional
    public Image addImage(ImageRequest imageRequest) {

        Image image = Image.builder()
                .fileLocation(imageRequest.getFileLocation())
                .build();

        return imageRepository.save(image);
    }

    @Override
    @Transactional
    public String editImage(Integer id, ImageRequest imageRequest) {

        Image image = imageRepository.findById(id).get();

        image.setFileLocation(imageRequest.getFileLocation());

        imageRepository.save(image);

        return "Image updated successfully";
    }

    @Override
    @Transactional
    public String deleteImage(Integer id) {
        imageRepository.deleteById(id);

        return "Image deleted successfully";
    }
}
