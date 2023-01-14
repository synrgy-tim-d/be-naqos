package com.binar.kelompokd.services;

import com.binar.kelompokd.models.entity.Image;
import com.binar.kelompokd.models.request.ImageRequest;
import com.binar.kelompokd.repos.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
    public Image getImageById(Integer id) {
        return imageRepository.findById(id).get();
    }

    @Override
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    @Override
    @Transactional
    public Image editImage(Integer id, ImageRequest imageRequest) {

        Image image = imageRepository.findById(id).get();

        image.setFileLocation(imageRequest.getFileLocation());

        return imageRepository.save(image);
    }

    @Override
    @Transactional
    public String deleteImage(Integer id) {
        imageRepository.deleteById(id);

        return "Image deleted successfully";
    }
}
