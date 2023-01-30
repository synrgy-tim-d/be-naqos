package com.binar.kelompokd.controllers;

import com.binar.kelompokd.interfaces.ImageService;
import com.binar.kelompokd.interfaces.KostService;
import com.binar.kelompokd.models.entity.Image;
import com.binar.kelompokd.models.entity.kost.Kost;
import com.binar.kelompokd.utils.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.*;

@AllArgsConstructor
@RestController
@Tag(name = "Image Managment", description = "APIs for Managing Image")
@RequestMapping("/images")
public class ImageController {
    private final static Logger logger = LoggerFactory.getLogger(ImageController.class);
    private ImageService imageService;
    private KostService kostService;

    @Autowired
    public Response templateCRUD;

    @Operation(summary = "Delete Image Kost", description = "Delete Image Kost", tags = {"Image Managment"})
    @DeleteMapping("/delete")
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> deleteImageKost(@RequestParam(name = "url") String url,
                                                       @RequestParam(name = "kostId") String kostId){
        Kost currentKost = kostService.getKostById(UUID.fromString(kostId));
        Image currentUrl = imageService.findImageKostByUrl(url);

        try {
            imageService.deleteImageKost(currentUrl, currentKost);
            logger.info("delete image success", currentUrl);
            return new ResponseEntity<>(templateCRUD.templateSukses("Image Deleted"), HttpStatus.OK);
        }catch (RuntimeException e){
            logger.error("delete image gagal", e);
            return new ResponseEntity<>(templateCRUD.notFound("Image Not Found"), HttpStatus.NOT_FOUND);
        }

    }
}
