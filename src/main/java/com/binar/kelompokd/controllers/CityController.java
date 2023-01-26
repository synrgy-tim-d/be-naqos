package com.binar.kelompokd.controllers;

import com.binar.kelompokd.models.entity.location.City;
import com.binar.kelompokd.interfaces.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "City Management", description = "APIs for Managing City")
@RequestMapping("/cities")
public class CityController {
    @Autowired
    CityService cityService;

    @Operation(summary = "Get All List City", tags = {"City Management"})
    @GetMapping()
    public ResponseEntity<?> getAllCities(){
        return new ResponseEntity<>(cityService.getAllCities(), HttpStatus.OK);
    }

    @Operation(summary = "Get City by Id", tags = {"City Management"})
    @GetMapping("/{id}")
    public ResponseEntity<?> getCityById(@PathVariable("id") @Schema(example = "1") Integer id){
        return new ResponseEntity<>(cityService.getCityById(id), HttpStatus.OK);
    }

    @Operation(summary = "Add City", tags = {"City Management"})
    @PostMapping()
    public ResponseEntity<?> saveCity(@RequestBody City city){
        return new ResponseEntity<>(cityService.save(city), HttpStatus.OK);
    }

    @Operation(summary = "Update City by Id", tags = {"City Management"})
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCity(@PathVariable("id") @Schema(example = "1") Integer id, @RequestBody City city){
        return new ResponseEntity<>(cityService.update(id, city), HttpStatus.OK);
    }

    @Operation(summary = "Delete City", tags = {"City Management"})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable("id") @Schema(example = "1") Integer id){
        cityService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
