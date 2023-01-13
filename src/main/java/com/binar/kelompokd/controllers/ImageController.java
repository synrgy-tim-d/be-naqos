package com.binar.kelompokd.controllers;

import com.binar.kelompokd.models.request.ImageRequest;
import com.binar.kelompokd.models.request.KostRoomRequest;
import com.binar.kelompokd.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    @Autowired
    ImageService imageService;

    @PostMapping()
    public ResponseEntity<?> addPhoto(@RequestBody ImageRequest imageRequest){
        return new ResponseEntity<>(imageService.addImage(imageRequest), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editImage(@PathVariable("id") Integer id, @RequestBody ImageRequest imageRequest){
        return new ResponseEntity<>(imageService.editImage(id, imageRequest), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteImage(@PathVariable("id") Integer id){
        return new ResponseEntity<>(imageService.deleteImage(id), HttpStatus.ACCEPTED);
    }
}
