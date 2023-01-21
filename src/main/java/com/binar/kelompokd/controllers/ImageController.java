package com.binar.kelompokd.controllers;

import com.binar.kelompokd.interfaces.ImageService;
import com.binar.kelompokd.interfaces.KostService;
import com.binar.kelompokd.models.entity.kost.Kost;
import com.binar.kelompokd.models.response.ImageResponse;
import com.binar.kelompokd.models.response.MessageResponse;
import com.binar.kelompokd.utils.Response;
import com.binar.kelompokd.utils.TemplateCRUD;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.util.*;

@AllArgsConstructor
@RestController
@RequestMapping("/image")
public class ImageController {
    private ImageService imageService;
    private KostService kostService;

    @Autowired
    public Response templateCRUD;

    @PostMapping("/add/kost")
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<MessageResponse> addImagesKost(@RequestParam MultipartFile[] files,
                                                       @RequestParam(name = "kostId") UUID kostId){
        List<String> urls = new ArrayList<>();
        Arrays.stream(files)
            .forEach(imageFile -> urls.add(imageService.uploadFileKost(imageFile)));
        Kost currentKost = kostService.getKostById(kostId);
        if (currentKost==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        for (String url : urls) {
            imageService.saveImageKostToDb(url, currentKost);
        }
        return new ResponseEntity<>(new MessageResponse("Upload Success"),HttpStatus.CREATED);
    }
}
