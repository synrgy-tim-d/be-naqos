package com.binar.kelompokd.controllers;

import com.binar.kelompokd.models.request.ImageRequest;
import com.binar.kelompokd.interfaces.ImageService;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    ImageService imageService;

    @PostMapping()
    public ResponseEntity<?> addPhoto(@RequestBody ImageRequest imageRequest){
        return new ResponseEntity<>(imageService.addImage(imageRequest), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> editImage(@PathVariable("id") @Schema(example = "1") Integer id, @RequestBody ImageRequest imageRequest){
        return new ResponseEntity<>(imageService.editImage(id, imageRequest), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteImage(@PathVariable("id") Integer id){
        imageService.deleteImage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<?> getAllImages(){
        return new ResponseEntity<>(imageService.getAllImages(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getImageById(@PathVariable("id") Integer id){
        return new ResponseEntity<>(imageService.getImageById(id), HttpStatus.OK);
    }
}
