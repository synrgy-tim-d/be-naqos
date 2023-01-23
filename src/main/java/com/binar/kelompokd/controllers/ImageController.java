package com.binar.kelompokd.controllers;

import com.binar.kelompokd.interfaces.ImageService;
import com.binar.kelompokd.interfaces.KostService;
import com.binar.kelompokd.models.entity.Image;
import com.binar.kelompokd.models.entity.kost.Kost;
import com.binar.kelompokd.models.response.ImageResponse;
import com.binar.kelompokd.models.response.MessageResponse;
import com.binar.kelompokd.utils.Response;
import com.binar.kelompokd.utils.TemplateCRUD;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Delete Image Kost", description = "Delete Image Kost")
    @DeleteMapping("/delete")
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> deleteImageKost(@RequestParam(name = "url") String url,
                                                       @RequestParam(name = "kostId") String kostId){
        Kost currentKost = kostService.getKostById(UUID.fromString(kostId));
        Image currentUrl = imageService.findImageKostByUrl(url);

        try {
            imageService.deleteImageKost(currentUrl, currentKost);
            return new ResponseEntity<>(templateCRUD.templateSukses("Image Deleted"), HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(templateCRUD.notFound("Image Not Found"), HttpStatus.NOT_FOUND);
        }

    }
}
