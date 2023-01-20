package com.binar.kelompokd.controllers;

import com.binar.kelompokd.models.request.RoomFacilityRequest;
import com.binar.kelompokd.interfaces.FacilityService;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/facility")
public class FacilityController {

    @Autowired
    FacilityService facilityService;

    @PostMapping()
    public ResponseEntity<?> addFacility(@RequestBody RoomFacilityRequest roomFacilityRequest){
        return new ResponseEntity<>(facilityService.addFacility(roomFacilityRequest), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getAllFacilities(){
        return new ResponseEntity<>(facilityService.getAllFacilities(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFacilityById(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id){
        return new ResponseEntity<>(facilityService.getFacilityById(id), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> editFacility(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id, @RequestBody RoomFacilityRequest roomFacilityRequest){
        return new ResponseEntity<>(facilityService.editFacility(id, roomFacilityRequest), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteFacility(@PathVariable("id") @Schema(example = "123e4567-e89b-12d3-a456-426614174000") UUID id){
        facilityService.deleteFacility(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
