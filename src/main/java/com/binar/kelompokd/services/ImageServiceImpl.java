package com.binar.kelompokd.services;

import com.binar.kelompokd.interfaces.ImageService;
import com.binar.kelompokd.models.entity.Image;
import com.binar.kelompokd.models.entity.kost.Kost;
import com.binar.kelompokd.repos.ImageRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {
    private Cloudinary cloudinary;
    @Autowired
    ImageRepository imageRepository;

    @Override
    public String uploadFileKost(MultipartFile image) {
        try {
            File uploadedFile = convertMultiPartToFile(image);
            Map params = ObjectUtils.asMap(
                "folder", "naqos/kost/");
            Map uploadResult = cloudinary.uploader().upload(uploadedFile, params);
            boolean isDeleted = uploadedFile.delete();
            if (isDeleted) {
                System.out.println("File is successfully deleted");
            } else
                System.out.println("File doesn't exist");
            return uploadResult.get("url").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String uploadFileAvatar(MultipartFile image) {
        try {
            File uploadedFile = convertMultiPartToFile(image);
            Map params = ObjectUtils.asMap(
                "folder", "naqos/avatar/");
            Map uploadResult = cloudinary.uploader().upload(uploadedFile, params);
            boolean isDeleted = uploadedFile.delete();
            if (isDeleted) {
                System.out.println("File is successfully deleted");
            } else
                System.out.println("File doesn't exist");
            return uploadResult.get("url").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    @Override
    public LinkedHashMap<String, Object> modifyJsonResponse(String requestType, String url) {
        LinkedHashMap<String, Object> jsonResponse = new LinkedHashMap<>();
        Image imageKost = imageRepository.findImageKostByUrl(url);
        if (requestType.equals("create")) {
            jsonResponse.put("status", "success");
            LinkedHashMap<String, String> data = new LinkedHashMap<>();
            data.put("message", "image successfully posted");
            data.put("createdAt", imageKost.getCreatedAt().toString());
            data.put("updatedAt", imageKost.getUpdatedAt().toString());
            data.put("url", url);
            jsonResponse.put("data", data);
        }

        if (requestType.equals("delete")) {
            jsonResponse.put("status", "success");
            LinkedHashMap<String, String> data = new LinkedHashMap<>();
            data.put("message", "image post successfully deleted");
            jsonResponse.put("data", data);
        }

        if (requestType.equals("get")) {

            jsonResponse.put("status", "success");
            LinkedHashMap<String, Object> data = new LinkedHashMap<>();
            data.put("createdAt", imageKost.getCreatedAt().toString());
            data.put("updatedAt", imageKost.getUpdatedAt().toString());
            data.put("url", imageKost.getUrl());
        }
        return jsonResponse;
    }

    @Override
    public void deleteImageKost(Image imageKost, Kost currentKost) {
        currentKost.deleteImageKost(imageKost);
        imageRepository.delete(imageKost);
    }

    @Override
    public Image findImageKostByUrl(String url) {
        return imageRepository.findImageKostByUrl(url);
    }

    @Override
    public void saveImageKostToDb(String url, Kost currentKost) {
        Image imageKost = new Image();
        imageKost.setUrl(url);
        imageKost.setKosts(currentKost);
        currentKost.add(imageKost);
        imageRepository.save(imageKost);
    }

    @Override
    public Image getImageById(Integer id) {
        return null;
    }
}
