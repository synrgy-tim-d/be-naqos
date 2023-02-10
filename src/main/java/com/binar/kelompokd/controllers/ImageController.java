package com.binar.kelompokd.controllers;

import com.binar.kelompokd.interfaces.INotificationService;
import com.binar.kelompokd.interfaces.IUserAuthService;
import com.binar.kelompokd.interfaces.ImageService;
import com.binar.kelompokd.interfaces.KostService;
import com.binar.kelompokd.models.entity.Image;
import com.binar.kelompokd.models.entity.kost.Kost;
import com.binar.kelompokd.models.entity.oauth.Users;
import com.binar.kelompokd.utils.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private INotificationService notificationService;
    private IUserAuthService userAuthService;

    @Autowired
    public Response templateCRUD;

    @Operation(summary = "Delete Image Kost", description = "Delete Image Kost", tags = {"Image Managment"})
    @DeleteMapping("/delete")
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> deleteImageKost(@RequestParam(name = "url") String url,
                                                       @RequestParam(name = "kostId") String kostId){
        Kost currentKost = kostService.getKostById(UUID.fromString(kostId));
        Image currentUrl = imageService.findImageKostByUrl(url);
        Users user = userAuthService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            imageService.deleteImageKost(currentUrl, currentKost);
            logger.info("delete image success", currentUrl);
            notificationService.saveNotification("Successfully Deleted Image Kost","Successfully deleted Image kost "+currentKost.getName(), user.getId());
            return new ResponseEntity<>(templateCRUD.templateSukses("Image Deleted"), HttpStatus.OK);
        }catch (RuntimeException e){
            logger.error("delete image gagal", e);
            notificationService.saveNotification("Failed Deleted Image Kost","Failed deleted Image kost "+currentKost.getName() + " Please try again", user.getId());
            return new ResponseEntity<>(templateCRUD.notFound("Image Not Found"), HttpStatus.NOT_FOUND);
        }

    }
}
