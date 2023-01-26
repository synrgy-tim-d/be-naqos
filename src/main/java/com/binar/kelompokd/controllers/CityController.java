package com.binar.kelompokd.controllers;

import com.binar.kelompokd.models.entity.location.City;
import com.binar.kelompokd.interfaces.CityService;
import com.binar.kelompokd.services.oauth.EmailSender;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/city")
public class CityController {
    @Autowired
    CityService cityService;

    @GetMapping()
    public ResponseEntity<?> getAllCities(){
        return new ResponseEntity<>(cityService.getAllCities(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCityById(@PathVariable("id") @Schema(example = "1") Integer id){
        return new ResponseEntity<>(cityService.getCityById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> saveCity(@RequestBody City city){
        return new ResponseEntity<>(cityService.save(city), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCity(@PathVariable("id") @Schema(example = "1") Integer id, @RequestBody City city){
        return new ResponseEntity<>(cityService.update(id, city), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable("id") @Schema(example = "1") Integer id){
        cityService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
