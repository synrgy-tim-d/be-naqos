package com.binar.kelompokd.interfaces;

import com.binar.kelompokd.models.entity.Image;
import com.binar.kelompokd.models.entity.kost.Kost;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

public interface ImageService {
    String uploadFileKost(MultipartFile image);

    File convertMultiPartToFile(MultipartFile file) throws IOException;

    LinkedHashMap<String, Object> modifyJsonResponse(String requestType, String url);

    void deleteImageKost(Image imageKost, Kost currentKost);

    Image findImageKostByUrl(String fileLocation);

    void saveImageKostToDb(String url, Kost currentKost);

    Image getImageById(Integer id);
}
